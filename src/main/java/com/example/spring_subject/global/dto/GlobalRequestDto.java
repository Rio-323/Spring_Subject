package com.example.spring_subject.global.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GlobalRequestDto {

    private String msg;
    private int statusCode;

    // 오류메세지 보내는 것 (실제로 쓰면 안됨.)
    public GlobalRequestDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
