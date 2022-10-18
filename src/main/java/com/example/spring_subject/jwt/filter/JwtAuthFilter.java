package com.example.spring_subject.jwt.filter;

import com.example.spring_subject.global.dto.GlobalRequestDto;
import com.example.spring_subject.jwt.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


        // 토큰이 있는지 확인
        if(accessToken != null) {
            if(!jwtUtil.tokenValidation ( accessToken )) { // 유효성 검사에서 false라면 if 내부 실행
               jwtExceptionHandler(response, "AccessToken Expired", HttpStatus.BAD_REQUEST );
                // 실행이 안되었다면 토큰에 문제가 없음.
                return;
            }
            // 토큰에 문제가 없으면 setAuth실행
            setAuthentication ( jwtUtil.getEmailFromToken (accessToken) );
        }else if (refreshToken != null) {
            if(!jwtUtil.refreshTokenValidation ( refreshToken )) { // 유효성 검사에서 false라면 if 내부 실행
                jwtExceptionHandler(response, "refreshToken Expired", HttpStatus.BAD_REQUEST );
                // 실행이 안되었다면 refresh 토큰 문제가 없음.
                return;
            }
            // 토큰에 문제가 없으면 setAuth실행
            setAuthentication ( jwtUtil.getEmailFromToken (refreshToken) );
        } // if else문 하나만으로 인증과 검증까지 실행.


        filterChain.doFilter ( request, response ); // 하지 않으면 filter가 끝나버림.(내부적으로 하는 filter가 실행되게 만들기 위함)
    }

    // 인증객체 생성
    public void setAuthentication(String email) {
        Authentication authentication = jwtUtil.crateAuthentication ( email );
        SecurityContextHolder.getContext ().setAuthentication ( authentication );
        // Security가 Holder를 통해 접근해서 authentication에 인증객체가 있는지 없는지 확인
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, HttpStatus status) {
        response.setStatus ( status.value () );
        response.setContentType ( "application/json" );

        try {
            String json = new ObjectMapper ().writeValueAsString ( new GlobalRequestDto (msg, status.value ()) );
            response.getWriter ().write ( json );
        } catch (Exception e) {
            log.error ( e.getMessage () );
        }
    }
}
