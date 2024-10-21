package com.bootcamp.demo.bc_forum2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.demo.bc_forum2.entity.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    

    Optional<PostEntity> findByWebsite(String website);


    List<PostEntity> findByUserId(Integer userId);
}