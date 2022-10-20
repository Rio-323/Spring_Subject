package com.example.spring_subject.entity;

import com.example.spring_subject.dto.AccountRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@RequiredArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    @JsonIgnore
    private String password;



    @OneToMany(mappedBy = "account")
    List<Post> post = new ArrayList<>();


    @OneToMany(mappedBy = "account")
    List<Comment> comment =new ArrayList<>();


    @OneToMany(mappedBy = "account")
    List<Heart> heart = new ArrayList<>();


    public Account(AccountRequestDto accountRequestDto) {
        this.email = accountRequestDto.getEmail();
        this.password = accountRequestDto.getPassword ();
    }

    public void serEncodePassword(String encodePassword) {this.password = encodePassword;}
}
