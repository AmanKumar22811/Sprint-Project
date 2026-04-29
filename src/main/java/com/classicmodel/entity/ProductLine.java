package com.classicmodel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "productLineEntity", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products = new ArrayList<>();

    public ProductLine() {}

    public String getProductLine() { return productLine; }
    public void setProductLine(String productLine) { this.productLine = productLine; }

    public String getTextDescription() { return textDescription; }
    public void setTextDescription(String textDescription) { this.textDescription = textDescription; }

    public String getHtmlDescription() { return htmlDescription; }
    public void setHtmlDescription(String htmlDescription) { this.htmlDescription = htmlDescription; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    @Override
    public String toString() {
        return "ProductLine{productLine='" + productLine + "'}";
    }
}
