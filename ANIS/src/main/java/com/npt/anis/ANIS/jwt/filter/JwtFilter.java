package com.npt.anis.ANIS.jwt.filter;

import com.npt.anis.ANIS.jwt.dto.CustomUserDetails;
import com.npt.anis.ANIS.jwt.util.JwtUtil;
import com.npt.anis.ANIS.member.domain.entity.Member;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info(request.getRequestURI());
        // '/api/test' 경로에 대한 요청이면 필터 체인을 계속 진행합니다.
//        if ("/test".equals(request.getRequestURI())) {
//            Authentication anonymousAuth = new AnonymousAuthenticationToken("anonymousUser", "anonymousUser",
//                    AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
//            SecurityContextHolder.getContext().setAuthentication(anonymousAuth);
//
//            filterChain.doFilter(request, response);
//            return;
//        }

        // 헤더에서 access키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("access");

        // 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {
            log.warn("access token not found");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            //response body
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 프론트와 통신이 같아야함
            return;
        }

        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) { // 엑세스 토큰이 아니라면

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰 검증 완료 됨
        // username, role 값을 획득
        String username = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        Member memberEntity = new Member();
        memberEntity.setStudentID(username);
        memberEntity.setRole(role);
        CustomUserDetails customUserDetails = new CustomUserDetails(memberEntity);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}

