package com.vikky.reactive;

public class CustomException extends Throwable {
    private String message;

    public CustomException(String exception) {
      this.message=exception;

    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
