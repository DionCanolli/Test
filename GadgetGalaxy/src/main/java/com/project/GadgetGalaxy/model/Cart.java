package com.project.GadgetGalaxy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemID;

    @ManyToOne
    @JoinColumn(name = "userID")
    @JsonIgnore
    private Users userCart;

    @ManyToOne
    @JoinColumn(name = "productID")
    @JsonIgnore
    private Product productCart;

    private int quantity;
    private double totalPrice;

    public Cart(Users userCart, Product productCart, int quantity, double totalPrice) {
        this.userCart = userCart;
        this.productCart = productCart;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Cart() {
    }

    public Long getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(Long cartItemID) {
        this.cartItemID = cartItemID;
    }

    public Users getUserCart() {
        return userCart;
    }

    public void setUserCart(Users userCart) {
        this.userCart = userCart;
    }

    public Product getProductCart() {
        return productCart;
    }

    public void setProductCart(Product productCart) {
        this.productCart = productCart;
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
