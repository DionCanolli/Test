package com.project.GadgetGalaxy.service;

import com.project.GadgetGalaxy.repository.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
public class ModelService {

    private final CartRepository cartRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final RolesRepository rolesRepository;
    private final TransactionsRepository transactionsRepository;
    private final UsersRepository usersRepository;
    private final WhishlistRepository whishlistRepository;
    private final CookieHolderRepository cookieHolderRepository;
    private final CookieWishlistRepository cookieWishlistRepository;
    private final CookieCartRepository cookieCartRepository;

    public ModelService(CartRepository cartRepository, CategoryRepository categoryRepository, ProductRepository productRepository, RolesRepository rolesRepository, TransactionsRepository transactionsRepository, UsersRepository usersRepository, WhishlistRepository whishlistRepository, CookieHolderRepository cookieHolderRepository, CookieWishlistRepository cookieWishlistRepository, CookieCartRepository cookieCartRepository) {
        this.cartRepository = cartRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.rolesRepository = rolesRepository;
        this.transactionsRepository = transactionsRepository;
        this.usersRepository = usersRepository;
        this.whishlistRepository = whishlistRepository;
        this.cookieHolderRepository = cookieHolderRepository;
        this.cookieWishlistRepository = cookieWishlistRepository;
        this.cookieCartRepository = cookieCartRepository;
    }
}
