package com.project.GadgetGalaxy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table
public class CookieCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cookieCartID;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CookieCartCookieValue", referencedColumnName = "cookieValue")
    @JsonIgnore
    private CookieHolder cookieCartCookieValue;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CookieCartProduct", referencedColumnName = "productId")
    @JsonIgnore
    private Product cookieCartProduct;

    private int quantity;
    private double totalPrice;

    public CookieCart(CookieHolder cookieCartCookieValue, Product cookieCartProduct, int quantity, double totalPrice) {
        this.cookieCartCookieValue = cookieCartCookieValue;
        this.cookieCartProduct = cookieCartProduct;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public CookieCart() {
    }

    public Long getCookieCartID() {
        return cookieCartID;
    }

    public void setCookieCartID(Long cookieCartID) {
        this.cookieCartID = cookieCartID;
    }

    public CookieHolder getCookieCartCookieValue() {
        return cookieCartCookieValue;
    }

    public void setCookieCartCookieValue(CookieHolder cookieCartCookieValue) {
        this.cookieCartCookieValue = cookieCartCookieValue;
    }

    public Product getCookieCartProduct() {
        return cookieCartProduct;
    }

    public void setCookieCartProduct(Product cookieCartProduct) {
        this.cookieCartProduct = cookieCartProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
