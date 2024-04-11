package com.npt.anis.ANIS.jwt.controller;

import com.npt.anis.ANIS.jwt.entity.RefreshEntity;
import com.npt.anis.ANIS.jwt.repository.RefreshRepository;
import com.npt.anis.ANIS.jwt.util.CookieUtil;
import com.npt.anis.ANIS.jwt.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class ReissueController {
    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final CookieUtil cookieUtil;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            //response status code
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST); // 프론트와 협업한 특정 응답코드 반환
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            //response status code
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {
            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        // DB에 해당 refresh token이 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            //response status code
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        Long accessExpiredMs = 1000 * 60 * 10L; // 10분
        Long refreshExpiredMs = 1000* 60 * 60 * 24L; // 1일
        String newAccess = jwtUtil.createJwt("access", username, role, accessExpiredMs);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, refreshExpiredMs);

        // Refresh 토큰 저장 DB에 기존의 refresh token 삭제 후 새로운 refresh token 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(username, refresh, refreshExpiredMs);

        //response
        response.setHeader("access", newAccess);
        response.addCookie(cookieUtil.createCookie("refresh", newRefresh));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setStudentID(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}
