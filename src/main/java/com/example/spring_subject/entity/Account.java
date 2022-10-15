package com.example.spring_subject.entity;

import com.example.spring_subject.dto.AccountRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

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
    private String password;

    public Account(AccountRequestDto accountRequestDto) {
        this.email = accountRequestDto.getEmail();
        this.password = accountRequestDto.getPassword ();
    }

    public void serEncodePassword(String encodePassword) {this.password = encodePassword;}
}
