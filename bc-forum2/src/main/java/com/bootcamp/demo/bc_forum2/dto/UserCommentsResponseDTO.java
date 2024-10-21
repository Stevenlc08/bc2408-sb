package com.bootcamp.demo.bc_forum2.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"id", "username","comments"})
public class UserCommentsResponseDTO {
  private Integer id;
  private String username;
  private List<CommentSummaryDTO> comments;
  
}
