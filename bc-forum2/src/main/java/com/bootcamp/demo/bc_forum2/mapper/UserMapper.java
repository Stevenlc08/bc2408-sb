package com.bootcamp.demo.bc_forum2.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.bootcamp.demo.bc_forum2.dto.UserDTO;
import com.bootcamp.demo.bc_forum2.entity.UserEntity;

import com.bootcamp.demo.bc_forum2.dto.PostDTO;
import com.bootcamp.demo.bc_forum2.entity.PostEntity;

@Component
public class UserMapper {

    private final PostMapper postMapper;

    public UserMapper(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public UserEntity toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        List<PostEntity> posts = userDTO.getPosts().stream()
                .map(postMapper::toEntity)
                .collect(Collectors.toList());

        return UserEntity.builder()
                .id(userDTO.getId())
                .name(truncate(userDTO.getName(), 255))
                .username(truncate(userDTO.getUsername(), 255))
                .email(truncate(userDTO.getEmail(), 255))
                .phone(truncate(userDTO.getPhone(), 255))
                .website(truncate(userDTO.getWebsite(), 500))
                .posts(posts)
                .build();
    }

    public UserDTO toDTO(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        List<PostDTO> postDTOs = userEntity.getPosts().stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());

        return UserDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .website(userEntity.getWebsite())
                .posts(postDTOs)
                .build();
    }


    private String truncate(String value, int maxLength) {
        if (value != null && value.length() > maxLength) {
            return value.substring(0, maxLength);
        }
        return value;
    }
}