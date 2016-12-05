package com.avenuecode.service;

import com.avenuecode.ProductView;
import com.avenuecode.dao.ProductDao;
import com.avenuecode.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Set;

@Component
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductEndpoint {

    @Autowired
    private ProductDao productDao;

    @POST
    @Transactional
    public Response createProduct(Product product) {
        productDao.save(product);
        URI uri = URI.create("/products/" + product.getId());
        return Response.created(uri).build();
    }

    @PUT
    @Transactional
    public Response updateProduct(Product product) {
        Product updatedProduct = productDao.update(product);
        return Response.ok(updatedProduct).build();
    }

    @DELETE
    @Transactional
    public Response deleteProduct(Product product) {
        productDao.delete(product);
        return Response.noContent().build();
    }

    @GET
    public Response getAllProducts(@HeaderParam("images") boolean images, @HeaderParam("parent") boolean parentProduct) throws JsonProcessingException {

        if (images || parentProduct) {
            Set<Product> allProducts = productDao.getAllProductsAnd(images, parentProduct);
            String json = new ObjectMapper().writerWithView(ProductView.RelationList.class).writeValueAsString(allProducts);
            return Response.ok(json).build();
        } else {
            Set<Product> allProducts = productDao.getAllProducts();
            String json = new ObjectMapper().writerWithView(ProductView.MainList.class).writeValueAsString(allProducts);
            return Response.ok(json).build();
        }
    }

//    @GET
//    public Product getProduct(){
//        return  null;
//    }
//
//    @GET
//    public Product getProductAndRelations(){
//        return  null;
//    }
//
//    @GET
//    public Set<Product> getChildProductsFor(Product product){
//        return null;
//    }
//
//    @GET
//    public Set<Image> getChildImagesFor(Product product){
//        return null;
//    }

}
