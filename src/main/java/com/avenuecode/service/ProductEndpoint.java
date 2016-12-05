package com.avenuecode.service;

import com.avenuecode.ProductView;
import com.avenuecode.dao.ImageDao;
import com.avenuecode.dao.ProductDao;
import com.avenuecode.model.Image;
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
    @Autowired
    private ImageDao imageDao;

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
        product = productDao.findById(product.getId());
        productDao.delete(product);
        return Response.noContent().build();
    }

    @GET
    public Response getAllProducts(@HeaderParam("images") boolean images,
                                   @HeaderParam("parent") boolean parentProduct) throws JsonProcessingException {
        if (images || parentProduct) {
            Set<Product> allProducts = productDao.findAllProductsWithFilters(images, parentProduct);
            String json = new ObjectMapper().writerWithView(ProductView.RelationList.class).writeValueAsString(allProducts);
            return Response.ok(json).build();
        } else {
            Set<Product> allProducts = productDao.getAllProducts();
            String json = new ObjectMapper().writerWithView(ProductView.MainList.class).writeValueAsString(allProducts);
            return Response.ok(json).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getProduct(@PathParam("id") Integer id, @HeaderParam("images") boolean images,
                               @HeaderParam("parent") boolean parentProduct) throws JsonProcessingException {
        if(images || parentProduct){
            Product product = productDao.findByIdWithFilters(id,images,parentProduct);
            String json = new ObjectMapper().writerWithView(ProductView.RelationList.class).writeValueAsString(product);
            return Response.ok(json).build();
        }else {
            Product product = productDao.findById(id);
            String json = new ObjectMapper().writerWithView(ProductView.MainList.class).writeValueAsString(product);
            return Response.ok(json).build();
        }
    }

    @GET
    @Path("/{id}/child")
    public Response getChildProducts(@PathParam("id") Integer id) throws JsonProcessingException {
        Set<Product> products = productDao.findChildProductsForProductWithId(id);
        String json = new ObjectMapper().writerWithView(ProductView.MainList.class).writeValueAsString(products);
        return Response.ok(json).build();
    }

    @GET
    @Path("/{id}/images")
    public Response getImagesFromProduct(@PathParam("id") Integer id) throws JsonProcessingException {
        Set<Image> images = imageDao.getImagesForProductId(id);
        String json = new ObjectMapper().writerWithView(ProductView.MainList.class).writeValueAsString(images);
        return Response.ok(json).build();
    }


}
