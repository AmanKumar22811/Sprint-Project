package com.classicmodel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "productlines")
public class ProductLine {

    @Id
    @Column(name = "productLine")
    @NotBlank
    private String productLine;

    @Column(name = "textDescription", length = 4000)
    private String textDescription;

    @Column(name = "htmlDescription", columnDefinition = "MEDIUMTEXT")
    private String htmlDescription;

    @Column(name = "image")
    private String imageUrl;
}

    