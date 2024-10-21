package com.bootcamp.demo.bc_forum2.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
  private Integer id;
  private Integer userId; //
  private String title;
  private String body;
  private String website;
  private List<CommentDTO> comments;
}
