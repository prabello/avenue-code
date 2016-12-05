package com.avenuecode.model;

import com.avenuecode.ProductView;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue
    @JsonView(ProductView.MainList.class)
    private Integer id;

    @JsonView(ProductView.MainList.class)
    private String name;

    @OneToMany
    @JsonView(ProductView.RelationList.class)
    private Set<Image> images;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(ProductView.RelationList.class)
    private Product parentProduct;

    @JsonCreator
    public Product(@JsonProperty("email") String name){
        this.name = name;
    }

    /**
     * Framework only
     */
    @Deprecated
    public Product(){}


    public Integer getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public Set<Image> getImages() {
        return images;
    }

    public Product getParentProduct() {
        return parentProduct;
    }
}
