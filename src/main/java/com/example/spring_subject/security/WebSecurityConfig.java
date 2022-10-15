package com.example.spring_subject.security;

import com.example.spring_subject.jwt.filter.JwtAuthFilter;
import com.example.spring_subject.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder ();} // 패스워드 사용

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {return (web) -> web.ignoring ().antMatchers ( "/h2-console/**" );} // filterchain보다 앞서서 걸림 h2는 인증이 필요없기 때문에

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors ();
        http.csrf ().disable ();

        http.sessionManagement ().sessionCreationPolicy ( SessionCreationPolicy.STATELESS ); // 세션 방식을 사용하지 않고 STATELESS(JWT)를 사용하는 것을 설정

        http.authorizeRequests ().antMatchers ( "/api/account/**" ).permitAll () // 로그인은 인증을 받지 않음
                .anyRequest ().authenticated () // 그외의 다른 사이트는 모두 인증을 받아야 함
                .and ().addFilterBefore ( new JwtAuthFilter ( jwtUtil ), UsernamePasswordAuthenticationFilter.class ); // JWT가 자체적으로 쓰는 filter보다 먼저 내가 넣어준 필터를 사용하는 것
                // UsernamePasswordAuthenticationFilter -> 스프링 시큐리티가 username과 password로 인증인가를 하는 메서드
        return http.build ();
    }
}
