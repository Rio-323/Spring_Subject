package com.example.spring_subject.jwt.util;

import com.example.spring_subject.entity.RefreshToken;
import com.example.spring_subject.jwt.dto.TokenDto;
import com.example.spring_subject.repository.RefreshTokenRepository;
import com.example.spring_subject.security.user.UserDetailsServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public static final String ACCESS_TOKEN = "Access_Token";
    public static final String REFRESH_TOKEN = "Refresh_Token";
    private static final long ACCESS_TIME = 10 * 1000L; // 1000L -> 1초
    private static final long REFRESH_TIME = 60 * 1000L; // 1000L -> 1초
    // Date가 Long 타입을 파라미터로 받기 때문에 Long으로 지정


    @Value("${jwt.secret.key}") // properties안에 값을 뽑아 올 수 있음
    private String secretKey;
    private Key key; // 키 객체에서 알고리즘을 받음
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // 알고리즘

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder ().decode ( secretKey ); // secretkey를 디코딩해서 배열로 초기화
        key = Keys.hmacShaKeyFor (bytes); // 키가 생성될 때 초기화
    }

    // header 토큰을 가져오는 기능
    public String getHeaderToken(HttpServletRequest request, String type) {
        return type.equals ( "Access" ) ? request.getHeader ( ACCESS_TOKEN ) : request.getHeader ( REFRESH_TOKEN );
    }


    // 토큰 생성

    public TokenDto createAllToken(String email) {
        return new TokenDto (createToken ( email,  "Access"), createToken ( email,  "Refresh"));
    }

    public String createToken(String email, String type) {

        // Expiration을 사용하기 위함.
        Date date = new Date ();

        long time = type.equals ( "Access" ) ? ACCESS_TIME : REFRESH_TIME; // ACCESS와 REFRESH가 시간이 달라야 하기때문에 삼항연산자로 처리

        // 토큰을 만들어주는 매소드
        return Jwts.builder ()
                .setSubject ( email )
                .setExpiration ( new Date (date.getTime () + time) )
                .setIssuedAt ( date )
                .signWith ( key, signatureAlgorithm ) // key 객체랑 알고리즘을 주어야 함.
                .compact ();
    }

    // 토큰 검증
    public Boolean tokenValidation(String token) {
        try {
            Jwts.parserBuilder ().setSigningKey ( key ).build ().parseClaimsJws ( token );
            return true;
        } catch (Exception ex) {
            log.error ( ex.getMessage () );
            return false;
        }
    }

    // refresh 토큰 검증
    public Boolean refreshTokenValidation(String token) {
        // 1차 토큰 검증
        if(!tokenValidation ( token )) return false;

        // DB에 저장한 refresh 토큰과 비교
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountEmail ( getEmailFromToken ( token ) );

        return refreshToken.isPresent () && token.equals ( refreshToken.get ().getRefreshToken () );
        // DB에 refreshToken이 존재하는지 확인하고 있다면 내가 보내준 토큰과 일치하는지 확인.

    }

    // 인증 객체 생성
    public Authentication crateAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername ( email );
        return new UsernamePasswordAuthenticationToken ( userDetails, "", userDetails.getAuthorities () );
        // 시큐리티 내부에 있는 매서드로 토큰 생성
    }


    // 토큰에서 email을 가져오는 기능
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder ().setSigningKey ( key ).build ().parseClaimsJws ( token ).getBody ().getSubject ();
    }
}
