package com.example.spring_subject.entity;


import com.example.spring_subject.dto.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;

    @ManyToOne
    private Account account;

    private String contents;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Comment> comment = new ArrayList<> ();

    public Post(PostDto postDto){
        this.title = postDto.getTitle();
        this.contents = postDto.getContents();
    }
    public Post(PostDto postDto,Account account){
        this.title = postDto.getTitle();
        this.account = account;
        this.contents = postDto.getContents();
    }

    public void update(PostDto postDto){
        this.title = postDto.getTitle();
        this.contents = postDto.getContents();
    }
}
