package com.example.spring_subject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class AccountRequestDto {

<<<<<<< HEAD
    @NotBlank
    @Size(min = 4, max = 60, message = "{account.nickname.size}")
    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "{account.nickname.pattern}")
    private String email;

    @NotBlank
    @Size(min = 8, max = 20, message = "{account.password.size}")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$"
            , message = "{account.password.pattern}")
    private String password;

    public AccountRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setEncodePwd(String encodePwd) {
        this.password = encodePwd;
    }
}
