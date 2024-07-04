package com.project.GadgetGalaxy.startup;

import com.project.GadgetGalaxy.model.CookieHolder;
import com.project.GadgetGalaxy.service.ModelService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CookieManager {


    private final ModelService modelService;

    public CookieManager(ModelService modelService) {
        this.modelService = modelService;
    }

    public void createCookieIfDoesntExist(HttpServletRequest request, HttpServletResponse response) {
            Cookie[] cookies = request.getCookies();
            boolean cookieExists = false;

            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("galaxyUser")) {
                        cookieExists = true;
                        break;
                    }
                }
                if (!cookieExists) {
                    String generatedValue = generateRandomValue();

                    Cookie cookie = new Cookie("galaxyUser", generatedValue);
                    cookie.setMaxAge(24 * 60 * 60 * 365);
                    cookie.setPath("/");
                    response.addCookie(cookie);

                    CookieHolder cookieHolder = new CookieHolder(generatedValue);
                    modelService.getCookieHolderRepository().save(cookieHolder);
                }
            }else {
                String generatedValue = generateRandomValue();

                Cookie cookie = new Cookie("galaxyUser", generatedValue);
                cookie.setMaxAge(24 * 60 * 60);
                cookie.setPath("/");
                response.addCookie(cookie);

                CookieHolder cookieHolder = new CookieHolder(generatedValue);
                modelService.getCookieHolderRepository().save(cookieHolder);
            }
    }

    public Cookie returnCookieByName(String name, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
        }
        return null;
    }

    private static String generateRandomValue() {
        String allowedChars = "0123456789aqzwsxedcrfvtgbyhnujmikolp";

        Random random = new Random();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 50; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            sb.append(allowedChars.charAt(randomIndex));
        }

        return sb.toString();
    }
}
