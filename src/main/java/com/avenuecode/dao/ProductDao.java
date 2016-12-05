package com.avenuecode.dao;


import com.avenuecode.model.Image;
import com.avenuecode.model.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("JpaQlInspection")
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

    public Product findByIdWithFilters(Integer id, boolean images, boolean parentProduct) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);

        if (images) {
            root.fetch("images", JoinType.LEFT);
        }
        if (parentProduct) {
            Fetch<Object, Object> joinParent = root.fetch("parentProduct", JoinType.LEFT);
            joinParent.fetch("images",JoinType.LEFT);
        }

        criteria.where(builder.equal(root.get("id"),id));

        return manager.createQuery(criteria).getSingleResult();
    }

    public Set<Product> findAllProductsWithFilters(boolean images, boolean parentProduct) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);

        if (images) {
            root.fetch("images", JoinType.LEFT);
        }
        if (parentProduct) {
            Fetch<Object, Object> joinParent = root.fetch("parentProduct", JoinType.LEFT);
            joinParent.fetch("images",JoinType.LEFT);
        }

        return new HashSet<>(manager.createQuery(criteria).getResultList());
    }

    public Product findById(Integer id) {
        return manager.find(Product.class, id);
    }

    public Set<Product> findChildProductsForProductWithId(Integer id) {
        return new HashSet<>(manager.createQuery("select p from Product p where p.parentProduct.id = :id",
                Product.class).setParameter("id", id).getResultList());
    }
}
