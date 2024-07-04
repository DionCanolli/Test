package com.project.GadgetGalaxy.controller;

import com.project.GadgetGalaxy.model.Category;
import com.project.GadgetGalaxy.model.Product;
import com.project.GadgetGalaxy.model.Transactions;
import com.project.GadgetGalaxy.model.Users;
import com.project.GadgetGalaxy.security.CustomUserDetails;
import com.project.GadgetGalaxy.service.ModelService;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class AdminController {

    private ModelService modelService;

    public AdminController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping(value = "/admin")
    public String openAdmin(Model model){

        List<Category> categories = modelService.getCategoryRepository().findAll();
        List<Transactions> transactions = modelService.getTransactionsRepository().findAll();
        List<Users> users = modelService.getUsersRepository().findAll();
        List<Product> products = modelService.getProductRepository().findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("transactions", transactions);
        model.addAttribute("users", users);
        model.addAttribute("products", products);

        return "admin";
    }

    @PostMapping(value = "/addProduct")
    public String addProduct(@RequestParam("productName") String productName,
                             @RequestParam("productCategory") String productCategory,
                             @RequestParam("productPrice") String productPrice,
                             @RequestParam("productShortDescription") String productShortDescription,
                             @RequestParam("productDescription") String productDescription,
                             @RequestParam("productQuantity") String productQuantity,
                             @RequestParam("productImage") MultipartFile productImage,
                             Model model, RedirectAttributes redirectAttributes) {

        Optional<Product> existingProduct = modelService.getProductRepository().findProductByProductName(productName);
        if(existingProduct.isPresent()){
            redirectAttributes.addFlashAttribute("productNameError", "Product Exists!");
            return "redirect:/admin";
        }

        Category category = modelService.getCategoryRepository().findCategoryByCategoryName(productCategory);

        String productNameWithoutSpaces = productName.replaceAll("\\s+", "");
        if (productNameWithoutSpaces.length() > 0) {
            productNameWithoutSpaces = Character
                    .toLowerCase(productNameWithoutSpaces.charAt(0)) + productNameWithoutSpaces.substring(1);
        }

        Product product = new Product(productName, category, Double.parseDouble(productPrice), productShortDescription,
                productDescription, Integer.parseInt(productQuantity), productNameWithoutSpaces);

        File directory = new File("C:/Users/HP/Desktop/Java Projects/GadgetGalaxy - tech e-commerce/images/");
        String fileExtension = StringUtils.getFilenameExtension(productImage.getOriginalFilename());

        File imageFile = new File(directory.getAbsolutePath() + File.separator + productNameWithoutSpaces + "." + fileExtension);
        try{
            modelService.getProductRepository().save(product);

            productImage.transferTo(imageFile);
        }catch (IOException e){
            return "redirect:/admin";
        }
        return "redirect:/admin";
    }

    @PostMapping(value = "/delete-user")
    public String deleteUser(@RequestParam String userEmail){
        Users user = modelService.getUsersRepository().findByUserEmail(userEmail);
        modelService.getUsersRepository().delete(user);

        return "redirect:/admin";
    }

    @PostMapping(value = "/delete-product")
    public String deleteProduct(@RequestParam String productName){
        Product product = modelService.getProductRepository().findProductByProductName(productName).get();
        modelService.getProductRepository().delete(product);

        Path path = Paths.get("C:/Users/HP/Desktop/Java Projects/GadgetGalaxy - tech e-commerce/images/" + productName
                + ".jpg");
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }
}
