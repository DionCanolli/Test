package com.project.GadgetGalaxy.repository;

import com.project.GadgetGalaxy.model.CookieCart;
import com.project.GadgetGalaxy.model.CookieWishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CookieCartRepository extends JpaRepository<CookieCart, Long> {

    @Query(value = "SELECT cc FROM CookieCart cc where cc.cookieCartCookieValue.cookieValue = :cookieValue " +
            "AND cc.cookieCartProduct.productName = :productName")
    public CookieCart findCookieCartByCookieValueAndProductName(@Param("cookieValue") String cookieValue,
                                                                @Param("productName") String productName);

    @Query(value = "SELECT cc FROM CookieCart cc where cc.cookieCartCookieValue.cookieValue = :cookieValue")
    public List<CookieCart> findCookieCartByCookieValue(@Param("cookieValue") String cookieValue);

    @Transactional
    @Modifying
    @Query(value = "UPDATE CookieCart cc set cc.quantity = cc.quantity + :additionalQuantity, cc.totalPrice = :totalPrice" +
            " WHERE cc.cookieCartCookieValue.cookieValue = :cookieValue AND cc.cookieCartProduct.productName = :productName")
    public void updateCookieCart(@Param("cookieValue") String cookieValue, @Param("productName") String productName,
                                 @Param("additionalQuantity") int additionalQuantity, @Param("totalPrice") double totalPrice);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM CookieCart cc WHERE cc.cookieCartCookieValue.cookieValue = :cookieValue " +
            "AND cc.cookieCartProduct.productName = :productName")
    public void deleteCookieCartByCookieValueProductName(@Param("cookieValue") String cookieValue,
                                                         @Param("productName") String productName);

    @Query(value = "SELECT SUM(cc.totalPrice) FROM CookieCart cc WHERE cc.cookieCartCookieValue.cookieValue = :cookieValue")
    public Integer getSumOfTotalPrice(@Param("cookieValue") String cookieValue);
}
