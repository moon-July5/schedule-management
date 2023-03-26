package com.group4.miniproject.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("Can't find UserAccount");
  }

  public UserNotFoundException(String msg) {
    super(msg);
  }

}
