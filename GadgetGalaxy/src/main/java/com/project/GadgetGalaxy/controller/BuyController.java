package com.project.GadgetGalaxy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class BuyController {

    private ModelService modelService;
    private ObjectMapper objectMapper;

    public BuyController(ModelService modelService, ObjectMapper objectMapper) {
        this.modelService = modelService;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/buy")
    public String openBuy(@Nullable @AuthenticationPrincipal CustomUserDetails customUserDetails, HttpServletRequest request,
                          HttpServletResponse response, Model model, @RequestParam(required = false) String productName,
                          @RequestParam(required = false) String quantitySelectedToBuy) {
        try {
            Users user = customUserDetails.getUser();
            List<Cart> cartList = new ArrayList<>();

            if (quantitySelectedToBuy == null) {
                cartList = modelService.getCartRepository().findCartByUserEmail(user.getUserEmail());
                model.addAttribute("cart", cartList);

                Integer totalPrice = modelService.getCartRepository().getSumOfTotalPrice(user.getUserEmail());
                if (totalPrice != null) {
                    model.addAttribute("totalPrice", totalPrice);
                }
            } else {
                Product product = modelService.getProductRepository().findProductByProductName(productName).get();
                Cart cart = new Cart(user, product, Integer.parseInt(quantitySelectedToBuy),
                        Double.parseDouble(quantitySelectedToBuy) * product.getProductPrice());
                cartList.add(cart);
                model.addAttribute("cart", cartList);
                model.addAttribute("totalPrice",
                        product.getProductPrice() * Double.parseDouble(quantitySelectedToBuy));
                model.addAttribute("productName", productName);
                model.addAttribute("quantitySelectedToBuy", quantitySelectedToBuy);
            }
        } catch (NullPointerException ex) {
            CookieManager cookieManager = new CookieManager(modelService);
            cookieManager.createCookieIfDoesntExist(request, response);
            List<CookieCart> cookieCartList = new ArrayList<>();

            String cookieValue = cookieManager.returnCookieByName("galaxyUser", request).getValue();

            if (quantitySelectedToBuy == null) {
                cookieCartList = modelService.getCookieCartRepository().findCookieCartByCookieValue(cookieValue);
                model.addAttribute("cookieCart", cookieCartList);

                Integer totalPrice = modelService.getCookieCartRepository().getSumOfTotalPrice(cookieValue);
                if (totalPrice != null) {
                    model.addAttribute("totalPrice", totalPrice);
                }
            } else {
                Product product = modelService.getProductRepository().findProductByProductName(productName).get();

                CookieHolder cookieHolder = modelService
                        .getCookieHolderRepository()
                        .findCookieHolderByCookieValue(cookieValue);

                CookieCart cookieCart = new CookieCart(cookieHolder, product, Integer.parseInt(quantitySelectedToBuy),
                        Double.parseDouble(quantitySelectedToBuy) * product.getProductPrice());
                cookieCartList.add(cookieCart);

                model.addAttribute("cookieCart", cookieCartList);
                model.addAttribute("totalPrice",
                        product.getProductPrice() * Double.parseDouble(quantitySelectedToBuy));
                model.addAttribute("productName", productName);
                model.addAttribute("quantitySelectedToBuy", quantitySelectedToBuy);
            }
        }
        return "buy";
    }

    @PostMapping(value = "/performPurchase")
    public String performPurchase(@Nullable @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                  @RequestParam(required = false) Map<String, String> params,
                                  HttpServletRequest request, @RequestParam(required = false) String productName,
                                  @RequestParam(required = false) String quantitySelectedToBuy) throws Exception {

        if (params.get("cardName").isEmpty() || params.get("cardNumber").isEmpty() || params.get("expirationDate").isEmpty()
                || params.get("cvv").isEmpty() || params.get("cartType").isEmpty()){
            if (productName != null && quantitySelectedToBuy != null){
                return "redirect:/buy?productName=" + productName + "&quantitySelectedToBuy=" + quantitySelectedToBuy;
            }else{
                return "redirect:/buy";
            }
        }

        try {
            Users user = customUserDetails.getUser();
            List<Cart> cartList = modelService.getCartRepository().findCartByUserEmail(user.getUserEmail());

            if (productName != null){
                Product product = modelService.getProductRepository().findProductByProductName(productName).get();
                Transactions transaction = new Transactions(user, product, Integer.parseInt(quantitySelectedToBuy),
                        Double.parseDouble(quantitySelectedToBuy) * product.getProductPrice());
                modelService.getTransactionsRepository().save(transaction);
                return "redirect:/cart";
            }

            for (Cart cart : cartList){
                Transactions transaction = new Transactions(user, cart.getProductCart(), cart.getQuantity(), cart.getTotalPrice());
                modelService.getTransactionsRepository().save(transaction);
                modelService.getCartRepository().delete(cart);
            }
        } catch (NullPointerException ex) {

            if (params.get("email").isEmpty()){
                if (productName != null && quantitySelectedToBuy != null){
                    return "redirect:/buy?productName=" + productName + "&quantitySelectedToBuy=" + quantitySelectedToBuy;
                }else{
                    return "redirect:/buy";
                }
            }
            CookieManager cookieManager = new CookieManager(modelService);
            String cookieValue = cookieManager.returnCookieByName("galaxyUser", request).getValue();

            List<CookieCart> cookieCartList = modelService.getCookieCartRepository().findCookieCartByCookieValue(cookieValue);

            if (productName != null){
                Product product = modelService.getProductRepository().findProductByProductName(productName).get();
                Transactions transaction = new Transactions(product, params.get("email"), Integer.parseInt(quantitySelectedToBuy),
                        Double.parseDouble(quantitySelectedToBuy) * product.getProductPrice());
                modelService.getTransactionsRepository().save(transaction);
                return "redirect:/cart";
            }

            for (CookieCart cookieCart : cookieCartList) {
                Transactions transaction = new Transactions(cookieCart.getCookieCartProduct(), params.get("email"),
                        cookieCart.getQuantity(), cookieCart.getTotalPrice());
                modelService.getTransactionsRepository().save(transaction);
                modelService.getCookieCartRepository().delete(cookieCart);
            }
        }
        return "redirect:/cart";
    }

}




















