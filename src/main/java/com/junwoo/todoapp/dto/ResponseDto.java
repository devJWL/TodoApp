package com.junwoo.todoapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {

  @Schema(description = "오류발생 API경로")
  private String uri;
  @Schema(description = "HTTP 상태코드", example = "200 OK")
  private HttpStatus httpStatus;
  @Schema(description = "message", example = "성공")
  private String message;
  @Schema(description = "data", example = "<T>")
  private T data;

  public String getHttpStatus() {
    return httpStatus.toString();
  }
}
