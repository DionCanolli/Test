package com.project.GadgetGalaxy.repository;

import com.project.GadgetGalaxy.model.CookieHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookieHolderRepository extends JpaRepository<CookieHolder, String> {
    CookieHolder findCookieHolderByCookieValue(String cookieValue);
}
