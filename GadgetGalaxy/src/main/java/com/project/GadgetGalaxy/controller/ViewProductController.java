package com.project.GadgetGalaxy.controller;

import com.project.GadgetGalaxy.model.Product;
import com.project.GadgetGalaxy.service.ModelService;
import com.project.GadgetGalaxy.startup.CookieManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ViewProductController {

    private final ModelService modelService;

    public ViewProductController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/view-product")
    public String viewProduct(@RequestParam String productName, @RequestParam(required = false) String category, Model model,
                              HttpServletRequest request, HttpServletResponse response){
        CookieManager cookieManager = new CookieManager(modelService);
        cookieManager.createCookieIfDoesntExist(request, response);

        Product product = modelService.getProductRepository().findProductByProductName(productName).get();

        model.addAttribute("product", product);
        model.addAttribute("category", category);

        return "product";
    }
}
