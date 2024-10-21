package com.bootcamp.demo.bc_forum2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.demo.bc_forum2.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    

    Optional<UserEntity> findByUsername(String username);


    Optional<UserEntity> findByEmail(String email);
}