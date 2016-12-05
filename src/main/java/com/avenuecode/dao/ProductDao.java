package com.avenuecode.dao;


import com.avenuecode.model.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.Set;

@Repository
public class ProductDao {

    @PersistenceContext
    private EntityManager manager;

    public void save(Product product) {
        manager.persist(product);
    }

    public Product update(Product product) {
        return manager.merge(product);
    }

    public void delete(Product product) {
        manager.remove(product);
    }

    public Set<Product> getAllProducts() {
        return new HashSet<>(manager.createQuery("select p from Product p", Product.class).getResultList());
    }

    public Set<Product> getAllProductsAnd(boolean images, boolean parentProduct) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);


        if (images) {
            root.fetch("images",JoinType.LEFT);
        }
        if (parentProduct) {
            root.fetch("parentProduct",JoinType.LEFT);
        }

        return new HashSet<>(manager.createQuery(criteria).getResultList());
    }
}
