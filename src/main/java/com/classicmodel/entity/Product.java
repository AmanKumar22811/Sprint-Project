package com.classicmodel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "productCode")
    @NotBlank
    private String productCode;

    @Column(name = "productName")
    @NotBlank
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productLine")
    private ProductLine productLineEntity;

    @Column(name = "productScale")
    @NotBlank
    private String productScale;

    @Column(name = "productVendor")
    @NotBlank
    private String productVendor;

    @Column(name = "productDescription", columnDefinition = "TEXT")
    @NotBlank
    private String productDescription;

    @Column(name = "quantityInStock")
    @NotNull
    private Short quantityInStock;

    @Column(name = "buyPrice")
    @NotNull
    private BigDecimal buyPrice;

    @Column(name = "MSRP")
    @NotNull
    private BigDecimal MSRP;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Product() {}

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public ProductLine getProductLineEntity() { return productLineEntity; }
    public void setProductLineEntity(ProductLine productLineEntity) { this.productLineEntity = productLineEntity; }

    public String getProductScale() { return productScale; }
    public void setProductScale(String productScale) { this.productScale = productScale; }

    public String getProductVendor() { return productVendor; }
    public void setProductVendor(String productVendor) { this.productVendor = productVendor; }

    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }

    public Short getQuantityInStock() { return quantityInStock; }
    public void setQuantityInStock(Short quantityInStock) { this.quantityInStock = quantityInStock; }

    public BigDecimal getBuyPrice() { return buyPrice; }
    public void setBuyPrice(BigDecimal buyPrice) { this.buyPrice = buyPrice; }

    public BigDecimal getMSRP() { return MSRP; }
    public void setMSRP(BigDecimal MSRP) { this.MSRP = MSRP; }

    public List<OrderDetail> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetail> orderDetails) { this.orderDetails = orderDetails; }

    @Override
    public String toString() {
        return "Product{productCode='" + productCode + "', productName='" + productName + "'}";
    }
}
