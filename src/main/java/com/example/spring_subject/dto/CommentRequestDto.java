package com.example.spring_subject.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long postid;
    private String comment;
    private String email;
}
