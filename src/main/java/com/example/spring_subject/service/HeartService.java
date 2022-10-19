package com.example.spring_subject.service;

import com.example.spring_subject.dto.HeartDto;
import com.example.spring_subject.entity.Account;
import com.example.spring_subject.entity.Heart;
import com.example.spring_subject.entity.Post;
import com.example.spring_subject.repository.AccountRepository;
import com.example.spring_subject.repository.HeartRepository;
import com.example.spring_subject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class HeartService {

    private final PostRepository postRepository;
    private final AccountRepository accountRepository;
    private final HeartRepository heartRepository;

    // 좋아요 누르기
    @Transactional
    public boolean clickToLike(Long postId, Long accountId) {
        Post post = getPost(postId);
        Account account = getAccount ( accountId );

        // 서버에 반환할 boolean
        boolean toggleLike;

        HeartDto heartDto = new HeartDto (post, account);
        Heart heart = new Heart (heartDto);
        int likeCnt = heart.getPost ().getLikeCnt();

        // 지금 로그인 되어있는 사용자가 해당 게시물에 좋아요를 누른적이 있는지 없는지.
        Heart findHeart = heartRepository.findByPostAndAccount ( post, account ).orElse ( null );

        if(findHeart == null) {
            heart.getPost ().setLikeCnt ( likeCnt+1 );

            heartRepository.save ( heart );
            toggleLike = true;
        } else {
            heart.getPost ().setLikeCnt ( likeCnt-1 );

            heartRepository.deleteById ( findHeart.getId () );
            toggleLike = false;
        }

        return toggleLike;
    }

    private Account getAccount(Long accountId) {
        Account account = accountRepository.findById ( accountId ).orElseThrow (
                () -> new IllegalArgumentException ("사용자가 존재하지 않습니다.")
        );

        return account;
    }

    private Post getPost(Long postId) {
        Post post = postRepository.findById ( postId ).orElseThrow (
                () -> new IllegalArgumentException ("게시글이 존재하지 않습니다.")
        );

        return post;
    }
}
