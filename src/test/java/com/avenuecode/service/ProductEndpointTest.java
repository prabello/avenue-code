package com.avenuecode.service;

import com.avenuecode.ProductView;
import com.avenuecode.builders.ProductBuilder;
import com.avenuecode.model.Image;
import com.avenuecode.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ProductEndpointTest {

    private Product product;
    private Product childProduct;

    @LocalServerPort
    private Integer port;

    @Before
    public void setUp() {
        RestAssured.port = port;

        product = new ProductBuilder("PS4")
                .images(new Image("http://lebes.vteximg.com.br/arquivos/ids/176356/560140-5.jpg"),
                        new Image("https://psmedia.playstation.com/is/image/psmedia/ps4-overview-screen-02-eu-06sep16?$MediaCarousel_LargeImage$"))
                .build();

        childProduct = new ProductBuilder("PS4 Controller")
                .images(new Image("http://edc8a286c4e27b5356e3-bb1139a233dd1615ca84f744f3688ee9.r69.cf5.rackcdn.com/ps4/shell_designs/solid/f-standard-solid.png"),
                        new Image("https://i5.walmartimages.com/asr/9a955cdc-666e-4cf6-8440-54c35376811f_1.fa74f6283bf73f55e0ab2395a30cd1dd.jpeg"))
                .build();
    }

    @Test
    public void createProduct() throws Exception {
        insertProductExpecting201(product);
    }

    @Test
    public void updateProduct() throws Exception {
        String productUri = insertProductExpecting201(product);
        Product product = getProduct(productUri);
        product.setImages(new HashSet<Image>(Arrays.asList(new Image("http://compass.xbox.com/assets/23/0d/230dc52a-8f0e-40bf-bbd1-c51fdb8371e3.png?n=Homepage-360-UA_Upgrade-big_1056x594.png"))));

        given().body(product).and().header("Content-Type", "application/json")
                .expect().statusCode(200).when().put("/products").andReturn().as(Product.class);
    }

    @Test
    public void deleteProduct() throws Exception {
        String productUri = insertProductExpecting201(product);
        Product product = getProduct(productUri);

        given().body(product).and().header("Content-Type", "application/json")
                .expect().statusCode(204).when().delete("/products");
    }

    private String insertProductExpecting201(Product product) throws JsonProcessingException {
        String json = new ObjectMapper().writerWithView(ProductView.RelationList.class).writeValueAsString(product);

        return given().body(json).and().header("Content-Type", "application/json")
                .expect().statusCode(201).when().post("/products").andReturn().header("Location");
    }

    private Product getProduct(String productUri) {
        return given().baseUri(productUri).and().expect().statusCode(200)
                .when().get().andReturn().as(Product.class);
    }

    @Test
    public void getAllProductsWithoutRelations() throws Exception {
        insertProductExpecting201(product);
        Product[] products = get("/products").andReturn().as(Product[].class);

        for (Product product : products) {
            assertNull(product.getParentProduct());
            assertNull(product.getImages());
        }
    }

    @Test
    public void getAllProductsWithRelations() throws Exception {
        String url = insertProductExpecting201(product);
        Product productReturned = getProduct(url);
        childProduct.setParent(productReturned);
        insertProductExpecting201(childProduct);

        Product[] products = given().headers("images", true, "parent", true).get("/products")
                .andReturn().as(Product[].class);

        boolean atLeastOneHasParentProduct = false;
        for (Product product : products) {
            assertNotNull(product.getImages());
            if (!atLeastOneHasParentProduct) {
                atLeastOneHasParentProduct = product.getParentProduct() != null;
            }
        }
        assertTrue(atLeastOneHasParentProduct);
    }


    @Test
    public void getProductWithoutRelations() throws Exception {
        String uri = insertProductExpecting201(product);
        Product product = getProduct(uri);
        assertNull(product.getImages());
        assertNull(product.getParentProduct());
    }

    @Test
    public void getProductWithRelations() throws Exception {
        String uri = insertProductAndChild();

        Product product = given().baseUri(uri).and().headers("images", true, "parent", true)
                .expect().statusCode(200).when().get().andReturn().as(Product.class);

        assertNotNull(product.getImages());
        assertNotNull(product.getParentProduct());
    }

    @Test
    public void getChildProducts() throws Exception {
        String uri = insertProductAndChild();

        Product[] products = given().baseUri(uri).and().expect().statusCode(200)
                .when().get("/child").andReturn().as(Product[].class);

        for (Product product : products) {
            assertTrue(product.getParentProduct().equals(product));
        }
    }

    @Test
    public void getImagesFromProduct() throws Exception {
        String uri = insertProductAndChild();

        Product[] products = given().baseUri(uri).and().expect().statusCode(200)
                .when().get("/images").andReturn().as(Product[].class);

        for (Product product : products) {
            assertFalse(product.getImages().isEmpty());
        }

    }

    private String insertProductAndChild() throws JsonProcessingException {
        String uri = insertProductExpecting201(product);
        Product productReturned = getProduct(uri);
        childProduct.setParent(productReturned);
        uri = insertProductExpecting201(childProduct);
        return uri;
    }

}