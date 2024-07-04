package com.project.GadgetGalaxy.controller;

import com.project.GadgetGalaxy.model.Users;
import com.project.GadgetGalaxy.security.CustomUserDetailsService;
import com.project.GadgetGalaxy.service.ModelService;
import com.project.GadgetGalaxy.startup.CookieManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final ModelService modelService;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public LoginController(ModelService modelService, CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.modelService = modelService;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/login")
    public String openLogin(@RequestParam(required = false) String error, Model model,
                            HttpServletRequest request, HttpServletResponse response){
        CookieManager cookieManager = new CookieManager(modelService);
        cookieManager.createCookieIfDoesntExist(request, response);

        Users user = new Users();
        model.addAttribute("user", user);
        if (error != null){
            model.addAttribute("errorMsg", "Bad Credentials!");
        }
        return "login";
    }
}