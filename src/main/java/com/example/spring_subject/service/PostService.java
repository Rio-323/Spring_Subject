package com.example.spring_subject.service;


import com.example.spring_subject.dto.PostDto;
import com.example.spring_subject.dto.PostResponseDto;
import com.example.spring_subject.entity.Account;
import com.example.spring_subject.entity.Post;
import com.example.spring_subject.jwt.util.JwtUtil;
import com.example.spring_subject.repository.AccountRepository;
import com.example.spring_subject.repository.PostRepository;
import com.example.spring_subject.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {


    private final PostRepository postRepository;
    private final AccountRepository accountRepository;

    //글 게시
    @Transactional
    public PostResponseDto post(PostDto requestDto, UserDetailsImpl userDetails){
        Account account =userDetails.getAccount();
        Post post = new Post(requestDto,account);
        postRepository.save(post);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;

    }
    //{
    //    id : 1,
    //    title: "제목입니다",
    //    contents: “내용입니다.”
    //    userid: id
    //    createdAt:”2020-04-11T11:12:30.686”
    //}

    //게시글 가져오기
    public List<Post> getPost() { return postRepository.findAllByOrderByCreatedAtDesc();}

    //게시글 하나 가져오기
    public Post getOne(Long id){
        return postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }



    //글 삭제하기
    public String deletePost(Long postId){
        Post delete = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당글이 없습니다")
        );
        postRepository.deleteById(postId);
        return "섹제된 게시글 번호 : "+postId;
    }

    //게시글 업데이트
    public Post update(Long id, PostDto postDto){
        Post update = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당글이 없습니다")
        );
        update.update(postDto);
        postRepository.save(update);
        return update;
    }




}
