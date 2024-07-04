package com.project.GadgetGalaxy.controller;

import com.project.GadgetGalaxy.model.Product;
import com.project.GadgetGalaxy.service.ModelService;
import com.project.GadgetGalaxy.startup.CookieManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductsController {

    private final ModelService modelService;

    public ProductsController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/products")
    public String openProducts(@RequestParam(required = false, defaultValue = "1") Integer page, Model model,
                               @RequestParam(required = false) String category,
                               @RequestParam(required = false) String searchValue,
                               HttpServletRequest request, HttpServletResponse response) {

        CookieManager cookieManager = new CookieManager(modelService);
        cookieManager.createCookieIfDoesntExist(request, response);

        if(page == null) page = 1;

        Page<Product> products;
        Pageable pageable = PageRequest.of(page - 1, 16);

        if (category != null && !category.isEmpty())
            if(searchValue != null && !searchValue.isEmpty())
                products = modelService.getProductRepository()
                        .findAllByProductCategoryCategoryNameAndProductName(category, searchValue, pageable);
            else
                products = modelService.getProductRepository().findAllByProductCategoryCategoryName(category, pageable);
        else
            if(searchValue != null && !searchValue.isEmpty())
                products = modelService.getProductRepository().findAllByProductName(searchValue, pageable);
            else
                products = modelService.getProductRepository().findAll(pageable);


        model.addAttribute("products", products);
        model.addAttribute("page", page);
        model.addAttribute("category", category);
        model.addAttribute("totalPages", products.getTotalPages());

        return "products";
    }

}
