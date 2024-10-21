package com.bootcamp.demo.bc_forum2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
  private Integer id;
  private Integer postId; //
  private String name;
  private String email;
  private String body;
}
