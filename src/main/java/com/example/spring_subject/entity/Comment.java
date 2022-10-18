package com.example.spring_subject.entity;

import com.example.spring_subject.dto.CommentRequestDto;
import com.example.spring_subject.jwt.filter.JwtAuthFilter;
import com.example.spring_subject.jwt.util.JwtUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity

public class Comment extends Timestamped{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String comment;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Comment(CommentRequestDto commentRequestDto, Post post, Account account){
        this.comment = commentRequestDto.getComment();
        this.post = post;
        this.account = account;
    }

    public void update(CommentRequestDto requestDto){
        this.comment = requestDto.getComment();
    }
}
