package com.example.spring_subject.controller;

import com.example.spring_subject.security.user.UserDetailsImpl;
import com.example.spring_subject.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;

    // 좋아요 클릭
    @PostMapping("/heart/{postId}")
    public ResponseEntity<Boolean> isHeart(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long accountId = userDetails.getAccount ().getId ();
        return new ResponseEntity<> ( heartService.clickToLike ( postId, accountId ), HttpStatus.OK );
    }
}
