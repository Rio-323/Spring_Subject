package com.example.spring_subject.service;

import com.example.spring_subject.dto.CommentRequestDto;
import com.example.spring_subject.dto.CommentResponseDto;
import com.example.spring_subject.entity.Comment;
import com.example.spring_subject.entity.Post;
import com.example.spring_subject.repository.CommentRepository;
import com.example.spring_subject.repository.PostRepository;
import com.example.spring_subject.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetailsImpl) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        Comment comment = new Comment(commentRequestDto, post, userDetailsImpl.getAccount());
        commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return commentResponseDto;
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, UserDetailsImpl userDetailsImpl) throws Exception {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        String email = comment.getAccount().getEmail();
        if(userDetailsImpl.getAccount().getEmail().equals(email)){
            comment.update(requestDto);
            return new CommentResponseDto(comment);
        }else {
            throw new Exception("작성자만 수정 가능합니다.");
        }

    }

    public String deleteComment(Long commentId, UserDetailsImpl userDetailsImpl) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(()->new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        String email = comment.getAccount().getEmail();
        if(userDetailsImpl.getAccount().getEmail().equals(email)){
            commentRepository.deleteById(commentId);
            return "댓글 삭제 완료";
        }else {
            throw new RuntimeException("작성자만 삭제 가능합니다.");
        }
    }
}
