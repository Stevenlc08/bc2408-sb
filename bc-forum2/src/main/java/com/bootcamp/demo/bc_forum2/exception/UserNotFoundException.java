package com.bootcamp.demo.bc_forum2.exception;

public class UserNotFoundException extends RuntimeException{
  
  public UserNotFoundException() {
    super("User not found.");
  }
}
