package com.project.GadgetGalaxy.model;

import jakarta.persistence.*;

@Entity
@Table
public class CookieWishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cookieWishlistID;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CookieWishlistCookieValue", referencedColumnName = "cookieValue")
    private CookieHolder cookieWishlistCookieValue;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CookieWishlistProduct", referencedColumnName = "productId")
    private Product cookieWishlistProduct;

    public CookieWishlist(CookieHolder cookieWishlistCookieValue, Product cookieWishlistProduct) {
        this.cookieWishlistCookieValue = cookieWishlistCookieValue;
        this.cookieWishlistProduct = cookieWishlistProduct;
    }

    public CookieWishlist() {
    }

    public Long getCookieWishlistID() {
        return cookieWishlistID;
    }

    public void setCookieWishlistID(Long cookieWishlistID) {
        this.cookieWishlistID = cookieWishlistID;
    }

    public CookieHolder getCookieWishlistCookieValue() {
        return cookieWishlistCookieValue;
    }

    public void setCookieWishlistCookieValue(CookieHolder cookieWishlistCookieValue) {
        this.cookieWishlistCookieValue = cookieWishlistCookieValue;
    }

    public Product getCookieWishlistProduct() {
        return cookieWishlistProduct;
    }

    public void setCookieWishlistProduct(Product cookieWishlistProduct) {
        this.cookieWishlistProduct = cookieWishlistProduct;
    }
}
