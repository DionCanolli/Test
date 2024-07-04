package com.project.GadgetGalaxy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private Users userTransaction;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product productTransaction;

    private String email;
    private int stock;
    private double totalPrice;

    @CreationTimestamp
    private LocalDateTime dateTimeOfTransaction;

    public Transactions(Users userTransaction, Product productTransaction, int stock, double totalPrice) {
        this.userTransaction = userTransaction;
        this.productTransaction = productTransaction;
        this.stock = stock;
        this.totalPrice = totalPrice;
    }

    public Transactions(Product productTransaction, String email, int stock, double totalPrice) {
        this.productTransaction = productTransaction;
        this.stock = stock;
        this.totalPrice = totalPrice;
        this.email = email;
    }
    public Transactions() {
    }
}
