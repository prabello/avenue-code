package com.avenuecode.dao;

import com.avenuecode.model.Image;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

@Repository
public class ImageDao {

    @PersistenceContext
    private EntityManager manager;

    public Set<Image> getImagesForProductId(Integer id) {
        return new HashSet<>(manager.createQuery("select i from Image i where i.product.id = :id", Image.class)
                .setParameter("id", id).getResultList());
    }
}
