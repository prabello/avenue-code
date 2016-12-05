package com.avenuecode.model;

import com.avenuecode.ProductView;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

    @Id
    @GeneratedValue
    @JsonView(ProductView.MainList.class)
    private Integer id;

    @JsonView(ProductView.MainList.class)
    @NotNull
    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JsonView(ProductView.RelationList.class)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
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

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public void setParent(Product parent) {
        this.parentProduct = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(parentProduct, product.parentProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parentProduct);
    }
}
