package com.example.spring_subject.dto;


import com.example.spring_subject.entity.Account;
import com.example.spring_subject.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {


    private Long postId;

    private Account account;

    private String title;

    private String contents;

    private LocalDateTime createdAt;


    public PostResponseDto(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.account = post.getAccount();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
    }

}
