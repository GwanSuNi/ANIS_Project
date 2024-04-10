package com.npt.anis.ANIS.jwt.util;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true); // 자바스크립트로 쿠키에 접근하지 못하도록
        cookie.setMaxAge(60*60*24); // 1 day
//        cookie.setSecure(true); // HTTPS 일 경우
//        cookie.setPath("/"); // 쿠키가 적용될 범위
        return cookie;
    }
}