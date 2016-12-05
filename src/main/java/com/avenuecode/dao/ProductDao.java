package com.avenuecode.dao;


import com.avenuecode.model.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

@Repository
public class ProductDao {

    @PersistenceContext
    private EntityManager manager;

    public void save(Product product){
        manager.persist(product);
    }

    public Product update(Product product){
        return manager.merge(product);
    }

    public void delete(Product product){
        manager.remove(product);
    }

    public Set<Product> getAllProducts(){
        return (Set<Product>) manager.createQuery("",Product.class).getResultList();
    }
}
