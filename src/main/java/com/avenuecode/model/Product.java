package com.avenuecode.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany
    private Set<Image> images;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product parentProduct;


    public Integer getId() {
        return id;
    }
}
