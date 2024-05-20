package com.npt.anis.ANIS.jwt.filter;

import com.npt.anis.ANIS.jwt.entity.RefreshEntity;
import com.npt.anis.ANIS.jwt.repository.RefreshRepository;
import com.npt.anis.ANIS.jwt.util.CookieUtil;
import com.npt.anis.ANIS.jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final CookieUtil cookieUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 클라이언트 요청에서 username, password 추출
        String username = obtainUsername(request);
        String password = "1234";
//        String password = obtainPassword(request);

        System.out.println(username);

        // 스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야함
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password, null);

        // AuthenticationManager에게 인증을 요청
        return authenticationManager.authenticate(authenticationToken);

        // 아래는 JSON 방식으로 로그인 요청을 받을 때 사용
//        try {
//            // 클라이언트 요청에서 JSON 형식의 username, password 추출
//            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
//            String username = user.getUsername();
//            String password = user.getPassword();
//
//            // 스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야함
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password, null);
//
//            // AuthenticationManager에게 인증을 요청
//            return authenticationManager.authenticate(authenticationToken);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // 유저 정보
        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority authority = iter.next();
        String role = authority.getAuthority();

        // 토큰 생성
        Long accessExpiredMs = 1000L * 60 * 10; // 10분
        String access = jwtUtil.createJwt("access", username, role, accessExpiredMs);
        Long refreshExpiredMs = 1000L * 60 * 60 * 24; // 1일
        String refresh = jwtUtil.createJwt("refresh", username, role, refreshExpiredMs);


        // refresh 토큰 저장
        addRefreshEntity(username, refresh, refreshExpiredMs);

        // 응답 설정
        response.addHeader("access", access);
        response.addCookie(cookieUtil.createCookie("refresh", refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setStudentID(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

