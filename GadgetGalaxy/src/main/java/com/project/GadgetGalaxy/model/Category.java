package com.project.GadgetGalaxy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryID;

    private String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    @OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> productList;

    public Category() {
    }
}
