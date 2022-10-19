package com.example.spring_subject.controller;

import com.example.spring_subject.dto.CommentRequestDto;
import com.example.spring_subject.entity.Comment;
import com.example.spring_subject.entity.Post;
import com.example.spring_subject.repository.CommentRepository;
import com.example.spring_subject.repository.PostRepository;
import com.example.spring_subject.security.user.UserDetailsImpl;
import com.example.spring_subject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.Member;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final PostRepository postRepository;

    @PostMapping("/comment/{id}")
    public Comment createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        Post post = postRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        Comment comment = new Comment(commentRequestDto, post, userDetailsImpl.getAccount());
        return commentRepository.save(comment);
    }

    @GetMapping("/comment")
    public List<Comment> getComment(){return commentRepository.findAllByOrderByCreatedAtDesc();}


    @PutMapping("/comment/{id}")
    public String updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl ) throws Exception {
        Comment comment = this.commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));
        String email = comment.getAccount().getEmail();
        if(userDetailsImpl.getAccount().getEmail().equals(email)){
            commentService.update(id, commentRequestDto);
        }else {
            throw new Exception("사용자가 일치하지 않습니다.");
        }
        return "댓글 수정완료";
    }

    @DeleteMapping("/comment/{id}")
    public String deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws Exception {

        Comment comment = this.commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id가 없습니다."));
        String email = comment.getAccount().getEmail();
        if(userDetailsImpl.getAccount().getEmail().equals(email)){
            commentRepository.deleteById(id);
        }else {throw new Exception("사용자가 일치하지 않습니다.");
        }
        return  "댓글삭제완료";
    }
}