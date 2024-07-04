package com.project.GadgetGalaxy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productID;

    private String productName;

    @ManyToOne
    @JoinColumn(name = "ProductCategory")
    @JsonIgnore
    private Category productCategory;

    @OneToMany(mappedBy = "productTransaction")
    @JsonIgnore
    private List<Transactions> transactionsOfUser;

    @OneToMany(mappedBy = "productCart")
    @JsonIgnore
    private List<Cart> cartsOfProduct;

    @OneToMany(mappedBy = "productWishlist")
    @JsonIgnore
    private List<Wishlist> wishlistsOfProduct;

    private double productPrice;
    private String productShortDescription;
    private String productDescription;
    private int productQuantity;
    private String productImage;

    public Product(String productName, Category productCategory, double productPrice, String productShortDescription, String productDescription, int productQuantity, String productImage) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productShortDescription = productShortDescription;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
        this.productImage = productImage;
    }

    public Product(String productName, double productPrice, String productShortDescription, String productDescription, int productQuantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productShortDescription = productShortDescription;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
    }

    public Product() {
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }

    public List<Transactions> getTransactionsOfUser() {
        return transactionsOfUser;
    }

    public void setTransactionsOfUser(List<Transactions> transactionsOfUser) {
        this.transactionsOfUser = transactionsOfUser;
    }

    public List<Cart> getCartsOfProduct() {
        return cartsOfProduct;
    }

    public void setCartsOfProduct(List<Cart> cartsOfProduct) {
        this.cartsOfProduct = cartsOfProduct;
    }

    public List<Wishlist> getWishlistsOfProduct() {
        return wishlistsOfProduct;
    }

    public void setWishlistsOfProduct(List<Wishlist> wishlistsOfProduct) {
        this.wishlistsOfProduct = wishlistsOfProduct;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductShortDescription() {
        return productShortDescription;
    }

    public void setProductShortDescription(String productShortDescription) {
        this.productShortDescription = productShortDescription;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
