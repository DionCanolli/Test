package com.project.GadgetGalaxy.model;

import jakarta.persistence.*;

@Entity
@Table
public class CookieHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cookieHolderID;

    private String cookieValue;

    public CookieHolder(String cookieValue) {
        this.cookieValue = cookieValue;
    }

    public CookieHolder() {
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;
    }

    public Long getCookieHolderID() {
        return cookieHolderID;
    }

    public void setCookieHolderID(Long cookieHolderID) {
        this.cookieHolderID = cookieHolderID;
    }
}
