package com.example.spring_subject.jwt.filter;

import com.example.spring_subject.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 토근 인증 로직
        String accessToken = jwtUtil.getHeaderToken ( request,"Access" );
        String refreshToken = jwtUtil.getHeaderToken (request, "Refresh");
        // Token 가져오는 것까지 구현



    }
}
