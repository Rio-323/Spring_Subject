package com.example.spring_subject.repository;

import com.example.spring_subject.entity.Account;
import com.example.spring_subject.entity.Heart;
import com.example.spring_subject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
   Optional<Heart> findByPostAndAccount (Post post, Account account);
}
