package com.bootcamp.demo.bc_forum2.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.bootcamp.demo.bc_forum2.dto.CommentDTO;
import com.bootcamp.demo.bc_forum2.dto.PostDTO;
import com.bootcamp.demo.bc_forum2.entity.CommentEntity;
import com.bootcamp.demo.bc_forum2.entity.PostEntity;


@Component
public class PostMapper {

    private final CommentMapper commentMapper;

    public PostMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public PostEntity toEntity(PostDTO postDTO) {
        if (postDTO == null) {
            return null;
        }

        List<CommentEntity> comments = postDTO.getComments() != null ? 
            postDTO.getComments().stream()
                    .map(commentMapper::toEntity)
                    .collect(Collectors.toList()) : null;

        return PostEntity.builder()
                .id(postDTO.getId())
                .title(truncate(postDTO.getTitle(), 255))
                .body(postDTO.getBody()) 
                .website(truncate(postDTO.getWebsite(), 500))
                .comments(comments)
                .build();
    }

    public PostDTO toDTO(PostEntity postEntity) {
        if (postEntity == null) {
            return null;
        }

        List<CommentDTO> commentDTOs = postEntity.getComments() != null ? 
            postEntity.getComments().stream()
                    .map(commentMapper::toDTO)
                    .collect(Collectors.toList()) : null;

        return PostDTO.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .body(postEntity.getBody())
                .website(postEntity.getWebsite())
                .comments(commentDTOs)
                .build();
    }


    private String truncate(String value, int maxLength) {
        if (value != null && value.length() > maxLength) {
            return value.substring(0, maxLength);
        }
        return value;
    }
}