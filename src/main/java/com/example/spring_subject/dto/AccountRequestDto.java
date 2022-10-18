package com.example.spring_subject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class AccountRequestDto {


    @NotBlank
    @Size(min = 4, max = 12, message = "{account.nickname.size}")
    @Pattern(regexp = "[a-z\\d]*${3,12}", message = "{member.nickname.pattern}")
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
