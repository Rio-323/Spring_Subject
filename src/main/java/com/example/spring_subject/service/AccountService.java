package com.example.spring_subject.service;

import com.example.spring_subject.dto.*;
import com.example.spring_subject.entity.*;
import com.example.spring_subject.global.dto.GlobalRequestDto;
import com.example.spring_subject.jwt.dto.TokenDto;
import com.example.spring_subject.jwt.util.JwtUtil;
import com.example.spring_subject.repository.AccountRepository;
import com.example.spring_subject.repository.RefreshTokenRepository;
import com.example.spring_subject.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public GlobalRequestDto signup(AccountRequestDto accountRequestDto) {
        // email 중복 검사
        if(accountRepository.findByEmail ( accountRequestDto.getEmail () ).isPresent ()) {
            throw new RuntimeException ("Overlap Check");
        };

        // 패스워드는 암호화해서 저장해야함.(법으로 정해져 있음)
        accountRequestDto.setEncodePwd ( passwordEncoder.encode (accountRequestDto.getPassword ()) );

        Account account = new Account (accountRequestDto);
        accountRepository.save ( account );

        return new GlobalRequestDto ("Success SignUp", HttpStatus.OK.value () ); // status code = 200
    }

    @Transactional
    public GlobalRequestDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        // 로그인 처리 후 토큰까지 생성 후 저장
        Account account = accountRepository.findByEmail ( loginRequestDto.getEmail () ).orElseThrow (
                () -> new RuntimeException ("Not Found Account")
        );

        // login할 때 넣어준 password 값과 account에 등록된 인코딩된 값과 비교
        if(!passwordEncoder.matches ( loginRequestDto.getPassword (), account.getPassword () )) { // 매치가 되지 않았다면
            throw new RuntimeException ("Not matches password");
        }

        // 로그인 된 후 토큰 발급
        TokenDto tokenDto = jwtUtil.createAllToken ( loginRequestDto.getEmail () );

        // refresh 토큰이 중복인지 확인
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountEmail ( loginRequestDto.getEmail () );

        if(refreshToken.isPresent ()) { // 만약에 refresh 토큰이 있다면 update
            refreshTokenRepository.save ( refreshToken.get ().updateToken ( tokenDto.getRefreshToken () ) );
        } else { // 없다면 새로 저장
            RefreshToken newToken = new RefreshToken (tokenDto.getRefreshToken (), loginRequestDto.getEmail () );
            refreshTokenRepository.save ( newToken );
        }

        setHeader ( response, tokenDto );

        return new GlobalRequestDto ("Success Login", HttpStatus.OK.value () );
    }


    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        // 토큰을 헤더에 세팅
        response.addHeader ( JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken () );
        response.addHeader ( JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken () );
    }


    @Transactional
    public AccountResponseDto mypage(UserDetailsImpl userDetails){
        Account account = accountRepository.findById(userDetails.getAccount().getId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 계정입니다")
        );
        List<Post> mypost = account.getPost();
        List<PostResponseDto> postlist = new ArrayList<>();
        for(Post post : mypost){
            postlist.add(new PostResponseDto(post));
        }
        List<Comment> myComment = account.getComment();
        List<CommentResponseDto> commentList = new ArrayList<>();
        for(Comment comment : myComment){
            commentList.add(new CommentResponseDto(comment));
        }
        List<Heart> myHeart = account.getHeart();
        List<Long> heartPostId = new ArrayList<>();
        for(Heart heart : myHeart){
            heartPostId.add(heart.getPost().getPostId());
        }

        return new AccountResponseDto(account.getEmail(), postlist, commentList, heartPostId);
    }
}
