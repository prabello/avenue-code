package com.avenuecode.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.URI;

@Entity
public class Image {

    @Id
    @GeneratedValue
    private Integer id;

    private URI uri;

}
