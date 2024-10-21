package com.bootcamp.demo.bc_forum2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.demo.bc_forum2.dto.UserCommentsResponseDTO;
import com.bootcamp.demo.bc_forum2.dto.UserDTO;
import com.bootcamp.demo.bc_forum2.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsersWithPostsAndComments();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") int userId) {
        UserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }


  
    @GetMapping("/comments")
    public ResponseEntity<UserCommentsResponseDTO> getCommentsByUserId(@RequestParam("userId") int userId) {
        UserCommentsResponseDTO response = userService.getCommentsByUserId(userId);
        return ResponseEntity.ok(response);
    }
}