package com.bootcamp.demo.bc_forum2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bootcamp.demo.bc_forum2.dto.CommentDTO;
import com.bootcamp.demo.bc_forum2.entity.CommentEntity;


@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDTO toDTO(CommentEntity comment);
    CommentEntity toEntity(CommentDTO commentDTO);
} 