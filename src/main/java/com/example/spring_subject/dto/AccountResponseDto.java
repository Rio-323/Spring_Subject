package com.example.spring_subject.dto;


import com.example.spring_subject.entity.Account;
import com.example.spring_subject.entity.Heart;
import com.example.spring_subject.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {


    private  String email;

    private List<PostResponseDto> post;

    private List<CommentResponseDto> comment;

    private List<Long> likes;


    private AccountResponseDto(Account account, List<PostResponseDto> post, List<CommentResponseDto> comment, List<Long> likecnt){
        this.email = account.getEmail();
        this.post = post;
        this.comment = comment;
        this.likes = likecnt;
    }





}
