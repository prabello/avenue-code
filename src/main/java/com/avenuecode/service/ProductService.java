package com.avenuecode.service;

import com.avenuecode.dao.ProductDao;
import com.avenuecode.model.Image;
import com.avenuecode.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Set;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @POST
    @Transactional
    public Response createProduct(Product product){
        productDao.save(product);
        URI uri = URI.create("/product/" + product.getId());
        return Response.created(uri).build();
    }

    @PUT
    @Transactional
    public Response updateProduct(Product product){
        Product updatedProduct = productDao.update(product);
        return Response.noContent().build();
    }

    @DELETE
    @Transactional
    public Response deleteProduct(Product product){
        productDao.delete(product);
        return Response.noContent().build();
    }

    @GET
    public Set<Product> getAllProducts(){
        return null;
    }

    @GET
    public Set<Product> getAllProductsAndRelations(){
        return  null;
    }

    @GET
    public Product getProduct(){
        return  null;
    }

    @GET
    public Product getProductAndRelations(){
        return  null;
    }

    @GET
    public Set<Product> getChildProductsFor(Product product){
        return null;
    }

    public Set<Image> getChildImagesFor(Product product){
        return null;
    }

}
