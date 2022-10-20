package com.example.spring_subject.controller;

import com.example.spring_subject.dto.CommentRequestDto;
import com.example.spring_subject.dto.CommentResponseDto;
import com.example.spring_subject.entity.Comment;
import com.example.spring_subject.repository.CommentRepository;
import com.example.spring_subject.repository.PostRepository;
import com.example.spring_subject.security.user.UserDetailsImpl;
import com.example.spring_subject.service.CommentService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/comment/{postId}")
    public CommentResponseDto createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return commentService.createComment(postId, commentRequestDto, userDetailsImpl);
    }

    @GetMapping("/comment")
    public List<Comment> getComment(){return commentRepository.findAllByOrderByCreatedAtDesc();}

    @PutMapping("/comment/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws Exception{
        return commentService.updateComment(commentId, commentRequestDto, userDetailsImpl);
    }



    @DeleteMapping("/comment/{commentId}")
    public String deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        commentService.deleteComment(commentId, userDetailsImpl);
        return "댓글 삭제 완료";
    }
}