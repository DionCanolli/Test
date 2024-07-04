package com.project.GadgetGalaxy.repository;

import com.project.GadgetGalaxy.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

//    @Query(value = "SELECT * FROM Category c WHERE c.categoryName = :categoryName")
    Category findCategoryByCategoryName(/*@Param("categoryName")*/ String categoryName);
}
