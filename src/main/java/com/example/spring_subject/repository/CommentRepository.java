package com.example.spring_subject.repository;

import com.example.spring_subject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository <Comment, Long>{
    List<Comment> findAllByOrderByCreatedAtDesc();
    List<Comment> findAllByOrderByModifiedAtDesc();
}