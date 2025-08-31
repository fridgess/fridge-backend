package com.fridge.fridgebackend.global.exception;

import com.fridge.fridgebackend.global.response.ErrorResponse;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 핸들러 - 전역 예외 처리를 위함
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // Validation 예외
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    BindingResult bindingResult = e.getBindingResult();

    List<String> errors = bindingResult.getFieldErrors().stream()
        .map(error -> String.format("[field=%s, rejected=%s, message=%s]",
            error.getField(),
            error.getRejectedValue(),
            error.getDefaultMessage()))
        .toList();

    log.warn("handleCustomException() in GlobalExceptionHandler throw ValidationException: {}",
        errors);

    return ResponseEntity
        .status(ErrorCode.INVALID_INPUT_VALUE.getStatus())
        .body(
            ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e, Map.of("validationError", errors)));
  }

  /**
   * 커스텀 비즈니스 예외에 대한 처리
   *
   * @param e
   * @return 해당 Error 코드에 대응하는 에러 응답
   */
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
    log.error("handleCustomException() in GlobalExceptionHandler throw CustomException", e);

    ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode(), e, e.getDetails());
    return ResponseEntity
        .status(e.getErrorCode().getStatus())
        .body(errorResponse);
  }

  /**
   * 전체 예외에 대한 처리 (마지막 캐치)
   *
   * @param e
   * @return 500 INTERNAL SERVER ERROR 응답
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.error("handleCustomException() in GlobalExceptionHandler throw Exception", e);

    return ResponseEntity
        .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
        .body(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e,
            Map.of("reason", "Unexpected error")));
  }

}
