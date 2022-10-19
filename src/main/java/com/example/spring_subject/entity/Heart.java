package com.example.spring_subject.entity;

import com.example.spring_subject.dto.HeartDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Heart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Heart(HeartDto heartDto) {
        this.account = heartDto.getAccount();
        this.post = heartDto.getPost();
    }


}
