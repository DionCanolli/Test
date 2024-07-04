package com.project.GadgetGalaxy.repository;

import com.project.GadgetGalaxy.model.CookieWishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CookieWishlistRepository extends JpaRepository<CookieWishlist, Long> {

    // Gjej krejt elementet e CookieWishlist ne baz te emrin te Cookies (qe ruhet ne CookieHolder, e cila ruhet ne CookieWishlist
    List<CookieWishlist> findAllCookieWishlistByCookieWishlistCookieValueCookieValue(String cookieValue);

    // Gjeje nje CookieWishlist by cookievalue and productid
    CookieWishlist findCookieWishlistByCookieWishlistCookieValueCookieValueAndCookieWishlistProductProductID(
            String cookieValue, Long productID);

    // Fshij produktin ne cookiewishlist ne baz te vleres se cookies (qe e unifikon kete cookie) dhe productName qe eshte
    // property e properties Product cookieWishlistProduct
    @Transactional
    @Modifying //  Indicates that this query modifies the data
    @Query(value = "DELETE FROM CookieWishlist cw WHERE cw.cookieWishlistCookieValue.cookieValue = :cookieValue" +
            " AND cw.cookieWishlistProduct.productName = :productName")
    void deleteCookieWishlistItemByCookieValueAndProductName(@Param("cookieValue") String cookieValue,
                                                             @Param("productName") String productName);
}
