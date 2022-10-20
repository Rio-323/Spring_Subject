package com.example.spring_subject.controller;


import com.example.spring_subject.dto.PostDto;
import com.example.spring_subject.dto.PostResponseDto;
import com.example.spring_subject.entity.Post;
import com.example.spring_subject.security.user.UserDetailsImpl;
import com.example.spring_subject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //게시글 전체조회
    @GetMapping("/post")
    public List<Post> getPost(){ return postService.getPost();}

    //게시글 한개조회
    @GetMapping("/post/{id}")
    public PostResponseDto getOne(@PathVariable Long id){
        return postService.getOne(id);
    }

    //게시글 생성
    @PostMapping("/post")
    public PostResponseDto post(@RequestBody PostDto postDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return postService.post(postDto, userDetails);
    }

    //게시글 업데이트
    @PutMapping("/post/{id}")
    public PostResponseDto update(@PathVariable Long id, @RequestBody PostDto postDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return  postService.update(id,postDto, userDetails.getAccount());
    }

    //게시글 삭제
    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        postService.deletePost(id, userDetails.getAccount());
        return "삭제된 아이디 : "+ id;
    }
}
