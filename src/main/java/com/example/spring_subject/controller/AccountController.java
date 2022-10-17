package com.example.spring_subject.controller;

import com.example.spring_subject.dto.AccountRequestDto;
import com.example.spring_subject.dto.LoginRequestDto;
import com.example.spring_subject.global.dto.GlobalRequestDto;
import com.example.spring_subject.jwt.util.JwtUtil;
import com.example.spring_subject.security.user.UserDetailsImpl;
import com.example.spring_subject.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    private final JwtUtil jwtUtil;

    @PostMapping("/account/signup")
    public GlobalRequestDto signup(@RequestBody @Valid AccountRequestDto accountRequestDto) {
        // Http Body안에 들어가 있는 값을 accountRequestDto 받아옴.
        return accountService.signup(accountRequestDto);
    }

    @PostMapping("/account/login")
    public GlobalRequestDto login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
        // HttpServletResponse -> 로그인 할때 Http안에 있는 response를 받아옴.

        return accountService.login(loginRequestDto, response);
    }

    // refresh 토큰이 만료되었을 때 access 토큰 발급하는 API
    @GetMapping("/issue/token")
    public GlobalRequestDto issuedToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response) {
        // @AuthenticationPrincipal을 사용하면 UserDetailsImpl을 가져올 수 있음(로그인한 사용자의 정보를 파라미터로 받고 싶을때 사용) - 현재 사용자 조회
        response.addHeader ( JwtUtil.ACCESS_TOKEN, jwtUtil.createToken ( userDetails.getAccount ().getEmail (), "Access" ) );

        return new GlobalRequestDto ("Success IssuedToken", HttpStatus.OK.value () );
    }

}
