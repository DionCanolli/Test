package com.project.GadgetGalaxy.controller;

import com.project.GadgetGalaxy.model.*;
import com.project.GadgetGalaxy.security.CustomUserDetails;
import com.project.GadgetGalaxy.service.ModelService;
import com.project.GadgetGalaxy.startup.CookieManager;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.awt.print.Pageable;
import java.util.List;

@Controller
public class WishlistController {

    private final ModelService modelService;

    public WishlistController(ModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping(value = "/add-to-wishlist")
    public String addToWishlist(@RequestParam String productName, @AuthenticationPrincipal CustomUserDetails customUserDetails,
                               HttpServletRequest request,  Model model){
        Product product = modelService.getProductRepository().findProductByProductName(productName).get();

        if (customUserDetails == null){
            CookieManager cookieManager = new CookieManager(modelService);

            String cookieValue = cookieManager.returnCookieByName("galaxyUser", request).getValue();

            CookieHolder cookieHolder = modelService
                    .getCookieHolderRepository()
                    .findCookieHolderByCookieValue(cookieValue);

            CookieWishlist cookieWishlist = new CookieWishlist(cookieHolder, product);

            CookieWishlist cookieWishlistExists = modelService.getCookieWishlistRepository()
                    .findCookieWishlistByCookieWishlistCookieValueCookieValueAndCookieWishlistProductProductID(
                            cookieValue, product.getProductID());

            if (cookieWishlistExists == null)
                modelService.getCookieWishlistRepository().save(cookieWishlist);
        }else{
            Users user = customUserDetails.getUser();

            Wishlist wishlist = new Wishlist(user, product);

            Wishlist wishlistExists = modelService.getWhishlistRepository()
                    .findWishlistByUserWishlistUserEmailAndProductWishlistProductID(
                            user.getUserEmail(), product.getProductID());

            if (wishlistExists == null)
                modelService.getWhishlistRepository().save(wishlist);
        }

        return "redirect:/products";
    }

    @GetMapping(value = "/wishlist")
    public String openWishlist(@Nullable @AuthenticationPrincipal CustomUserDetails customUserDetails, HttpServletRequest request,
                               Model model){
        try{
            Users loggedInUser = customUserDetails.getUser(); // Nese kthen null, i bie qe ska user te bere login

            List<Wishlist> wishlistOfUser = modelService.getWhishlistRepository()
                    .findAllWishlistByUserWishlistUserEmail(loggedInUser.getUserEmail());

            model.addAttribute("wishlist", wishlistOfUser);
            model.addAttribute("wishlistSize", wishlistOfUser.size());
        }catch (NullPointerException ex){
            CookieManager cookieManager = new CookieManager(modelService);
            String cookieValue = cookieManager.returnCookieByName("galaxyUser", request).getValue();

            List<CookieWishlist> cookieWishlistList = modelService.getCookieWishlistRepository()
                    .findAllCookieWishlistByCookieWishlistCookieValueCookieValue(cookieValue);

            model.addAttribute("cookieWishlist", cookieWishlistList);
            model.addAttribute("cookieWishlistSize", cookieWishlistList.size());
        }

        return "wishlist";
    }

    @GetMapping("/delete-item-from-wishlist")
    public String deleteItemFromWishlist(@Nullable @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                         @RequestParam String productName, HttpServletRequest request,
                                         HttpServletResponse response) {
        try {
            Users loggedInUser = customUserDetails.getUser();
            modelService.getWhishlistRepository()
                    .deleteWishlistItemByUserEmailAndProductName(loggedInUser.getUserEmail(), productName);
        } catch (NullPointerException ex) {
            CookieManager cookieManager = new CookieManager(modelService);
            cookieManager.createCookieIfDoesntExist(request, response);

            var cookie = cookieManager.returnCookieByName("galaxyUser", request);
            String cookieValue = cookie.getValue();

            modelService.getCookieWishlistRepository()
                    .deleteCookieWishlistItemByCookieValueAndProductName(cookieValue, productName);
        }

        return "redirect:/wishlist";
    }
}
