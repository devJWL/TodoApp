package com.junwoo.todoapp.controller;


import com.junwoo.todoapp.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request
  ) {

    StringBuffer sb = new StringBuffer();
    ex.getAllErrors().forEach(error -> sb.append(error.getDefaultMessage() + " "));
    sb.setLength(sb.length() - 1);

    return ResponseEntity
        .badRequest()
        .body(ResponseDto
            .builder()
            .uri(((ServletWebRequest) request).getRequest().getRequestURI())
            .httpStatus(HttpStatus.BAD_REQUEST)
            .message(sb.toString())
            .data(null)
            .build()
        );
  }

  @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class,
      UsernameNotFoundException.class})
  public ResponseEntity<ResponseDto<?>> handlerRequestError(
      Exception e,
      HttpServletRequest request
  ) {
    return ResponseEntity
        .badRequest()
        .body(ResponseDto
            .builder()
            .uri(request.getRequestURI())
            .httpStatus(HttpStatus.BAD_REQUEST)
            .message(e.getMessage())
            .data(null)
            .build()
        );
  }
}
