package com.project.GadgetGalaxy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistItemID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private Users userWishlist;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product productWishlist;

    public Wishlist(Users userWishlist, Product productWishlist) {
        this.userWishlist = userWishlist;
        this.productWishlist = productWishlist;
    }

    public Wishlist() {
    }
}
