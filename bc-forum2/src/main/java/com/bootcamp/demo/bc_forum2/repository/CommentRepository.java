package com.bootcamp.demo.bc_forum2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.demo.bc_forum2.entity.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    

    Optional<CommentEntity> findByEmail(String email);
    List<CommentEntity> findByPostId(Integer postId);
}