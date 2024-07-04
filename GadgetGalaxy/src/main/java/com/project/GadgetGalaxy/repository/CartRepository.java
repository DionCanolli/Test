package com.project.GadgetGalaxy.repository;

import com.project.GadgetGalaxy.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT c FROM Cart c where c.userCart.userEmail = :userEmail " +
            "AND c.productCart.productName = :productName")
    public Cart findCartItemByUserEmailAndProductName(@Param("userEmail") String userEmail,
                                                      @Param("productName") String productName);

    @Query(value = "SELECT c FROM Cart c where c.userCart.userEmail = :userEmail " +
            "AND c.productCart.productName = :productName")
    public List<Cart> findAllCartItemsByUserEmailAndProductName(@Param("userEmail") String userEmail,
                                                            @Param("productName") String productName);
    @Query(value = "SELECT c FROM Cart c where c.userCart.userEmail = :userEmail")
    public List<Cart> findCartByUserEmail(@Param("userEmail") String userEmail);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Cart c set c.quantity = c.quantity + :additionalQuantity, c.totalPrice = :totalPrice" +
            " WHERE c.userCart.userEmail = :userEmail AND c.productCart.productName = :productName")
    public void updateCart(@Param("userEmail") String userEmail, @Param("productName") String productName,
                           @Param("additionalQuantity") int additionalQuantity, @Param("totalPrice") double totalPrice);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Cart c WHERE c.userCart.userEmail = :userEmail AND c.productCart.productName = :productName")
    public void deleteCartByUserEmailProductName(@Param("userEmail") String userEmail, @Param("productName") String productName);

    @Query(value = "SELECT SUM(c.totalPrice) FROM Cart c WHERE c.userCart.userEmail = :userEmail")
    public Integer getSumOfTotalPrice(@Param("userEmail") String userEmail);
}
