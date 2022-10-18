package com.example.spring_subject.service;

import com.example.spring_subject.dto.CommentRequestDto;
import com.example.spring_subject.entity.Comment;
import com.example.spring_subject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    @Transactional
    public Long update(Long postid, CommentRequestDto requestDto){
        Comment comment = commentRepository.findById(postid).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        comment.update(requestDto);
        return comment.getId();
    }
}
