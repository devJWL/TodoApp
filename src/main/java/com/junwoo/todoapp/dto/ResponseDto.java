package com.junwoo.todoapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {

  private String uri;
  private HttpStatus httpStatus;
  private String message;
  private T data;

  public String getHttpStatus() {
    return httpStatus.toString();
  }
}
