package com.example.spring_subject.dto;

import com.example.spring_subject.entity.Account;
import com.example.spring_subject.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeartDto {

    private Post post;
    private Account account;
}
