package com.project.GadgetGalaxy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.GadgetGalaxy.model.*;
import com.project.GadgetGalaxy.security.CustomUserDetails;
import com.project.GadgetGalaxy.service.ModelService;
import com.project.GadgetGalaxy.startup.CookieManager;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
public class CartController {

    private ModelService modelService;
    private ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    public CartController(ModelService modelService, ObjectMapper objectMapper) {
        this.modelService = modelService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/cart")
    public String openCart(@Nullable @AuthenticationPrincipal CustomUserDetails customUserDetails, HttpServletRequest request,
                           HttpServletResponse response, Model model) throws Exception{
        try{
            Users user = customUserDetails.getUser();

            List<Cart> cart = modelService.getCartRepository().findCartByUserEmail(user.getUserEmail());
            model.addAttribute("cart", cart);

            Integer totalPrice = modelService.getCartRepository().getSumOfTotalPrice(user.getUserEmail());
            if (totalPrice != null){
                model.addAttribute("totalPrice", totalPrice);
            }

        }catch (NullPointerException ex){
            CookieManager cookieManager = new CookieManager(modelService);
            cookieManager.createCookieIfDoesntExist(request, response);

            String cookieValue = cookieManager.returnCookieByName("galaxyUser", request).getValue();

            List<CookieCart> cookieCart = modelService.getCookieCartRepository().findCookieCartByCookieValue(cookieValue);
            model.addAttribute("cookieCart", cookieCart);

            Integer totalPrice = modelService.getCookieCartRepository().getSumOfTotalPrice(cookieValue);
            if (totalPrice != null){
                model.addAttribute("totalPrice", totalPrice);
            }
        }
        return "cart";
    }

    @PostMapping(value = "/add-to-cart")
    public String addToCart(@Nullable @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam String productName,
                            @RequestParam String quantitySelected, HttpServletRequest request){
        Product product = modelService.getProductRepository().findProductByProductName(productName).get();

        if (customUserDetails == null){
            CookieManager cookieManager = new CookieManager(modelService);

            String cookieValue = cookieManager.returnCookieByName("galaxyUser", request).getValue();

            CookieHolder cookieHolder = modelService
                    .getCookieHolderRepository()
                    .findCookieHolderByCookieValue(cookieValue);


            CookieCart cookieCart = new CookieCart(cookieHolder, product, Integer.parseInt(quantitySelected),
                    Double.parseDouble(quantitySelected) * product.getProductPrice());

            CookieCart cookieCartExists = modelService.getCookieCartRepository()
                    .findCookieCartByCookieValueAndProductName(cookieValue, product.getProductName());

            if (cookieCartExists == null)
                modelService.getCookieCartRepository().save(cookieCart);
            else
                modelService.getCookieCartRepository()
                        .updateCookieCart(cookieValue, product.getProductName(), Integer.parseInt(quantitySelected),
                                Double.parseDouble(quantitySelected) * product.getProductPrice());
        }else{
            Users user = customUserDetails.getUser();

            Cart cart = new Cart(user, product, Integer.parseInt(quantitySelected),
                    Double.parseDouble(quantitySelected) * product.getProductPrice());

            Cart cartExists = modelService.getCartRepository()
                    .findCartItemByUserEmailAndProductName(user.getUserEmail(), product.getProductName());

            if (cartExists == null)
                modelService.getCartRepository().save(cart);
            else
                modelService.getCartRepository()
                        .updateCart(user.getUserEmail(), product.getProductName(), Integer.parseInt(quantitySelected),
                                Double.parseDouble(quantitySelected) * product.getProductPrice());
        }
        return "redirect:/cart";
    }

    @GetMapping(value = "/delete-cart-item")
    public String deleteCartItem(@Nullable @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                 @RequestParam String productName, HttpServletRequest request){
        try {
            Users loggedInUser = customUserDetails.getUser();
            modelService.getCartRepository()
                    .deleteCartByUserEmailProductName(loggedInUser.getUserEmail(), productName);
        } catch (NullPointerException ex) {
            CookieManager cookieManager = new CookieManager(modelService);
            var cookie = cookieManager.returnCookieByName("galaxyUser", request);
            String cookieValue = cookie.getValue();

            modelService.getCookieCartRepository()
                    .deleteCookieCartByCookieValueProductName(cookieValue, productName);
        }
        return "redirect:/cart";
    }
}
