package com.project.GadgetGalaxy.controller;

import com.project.GadgetGalaxy.model.Roles;
import com.project.GadgetGalaxy.model.Users;
import com.project.GadgetGalaxy.security.CustomUserDetails;
import com.project.GadgetGalaxy.service.ModelService;
import com.project.GadgetGalaxy.startup.CookieManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final ModelService modelService;
    private PasswordEncoder passwordEncoder;

    public RegisterController(ModelService modelService, @Lazy PasswordEncoder passwordEncoder) {
        this.modelService = modelService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String openRegister(Model model, HttpServletRequest request, HttpServletResponse response){
        CookieManager cookieManager = new CookieManager(modelService);
        cookieManager.createCookieIfDoesntExist(request, response);

        Users user = new Users();
        model.addAttribute("theUser", user);

        return "register";
    }

    @PostMapping("/do-register")
    public String register(@Valid @ModelAttribute("theUser") Users theUser, BindingResult bindingResult, Model model){

        theUser.setUserPassword(passwordEncoder.encode(theUser.getUserPassword()));

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!passwordEncoder.matches(theUser.getConfirmPassword(), theUser.getUserPassword())){
            bindingResult.rejectValue("confirmPassword", null, "Passwords do not match");
            return "register";
        }

        if (theUser.getConfirmPassword().length() < 6){
            bindingResult.rejectValue("userPassword", null, "Password must have +6 characters!");
            return "register";
        }

        if (modelService.getUsersRepository().findByUserEmail(theUser.getUserEmail()) != null){
            bindingResult.rejectValue("userEmail", null, "Email exists");
            return "register";
        }


        Roles role = modelService.getRolesRepository().findByRoleName("ROLE_USER");
        theUser.setUserRole(role);

        modelService.getUsersRepository().save(theUser);

        return "redirect:/login";
    }
}