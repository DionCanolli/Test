package com.project.GadgetGalaxy.repository;

import com.project.GadgetGalaxy.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByProductCategoryCategoryName(String categoryName, Pageable pageable);
    Page<Product> findAllByProductCategoryCategoryNameAndProductName(String categoryName, String productName, Pageable pageable);
    Page<Product> findAllByProductName(String productName, Pageable pageable);

    Optional<Product> findProductByProductName(String productName);
}