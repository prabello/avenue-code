package com.avenuecode.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.net.URI;

@Entity
public class Image {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private URI uri;

    @ManyToOne
    private Product product;

    @Deprecated
    public Image(){

    }

    public Image(String uri){
        this.uri = URI.create(uri);
    }

    public URI getUri() {
        return uri;
    }
}
