package com.example.spring_subject.controller;


import com.example.spring_subject.dto.PostDto;
import com.example.spring_subject.entity.Post;
import com.example.spring_subject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Post getOne(Long id){
        return postService.getOne(id);
    }


    //게시글 생성
    @PostMapping("/post")
    public Post post(@RequestBody PostDto postDto){

        return postService.post(postDto);
    }

    //게시글 업데이트
    @PutMapping("/post/update/{id}")
    public Post update(@PathVariable Long id,@RequestBody PostDto postDto){

        return  postService.update(id,postDto);
    }

    //게시글 삭제
    @DeleteMapping("/post/delete/{id}")
    public String delete(@PathVariable Long id){
        postService.deletePost(id);
        return "삭제된 아이디 : "+ id;
    }


}
