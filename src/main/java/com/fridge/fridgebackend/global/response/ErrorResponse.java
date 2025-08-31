package com.fridge.fridgebackend.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fridge.fridgebackend.global.exception.CustomException;
import java.util.Map;
import lombok.Getter;

/**
 * 에러 응답 클래스
 *
 * @param <T>
 */
@Getter
@JsonInclude(Include.NON_EMPTY)
public class ErrorResponse extends CustomResponse {

  private final Map<String, Object> details;

  private ErrorResponse(ResultCode errorCode, Map<String, Object> details) {
    super(errorCode, false);
    this.details = details.isEmpty() ? Map.of() : Map.copyOf(details);
  }

  public static ErrorResponse of(ResultCode errorCode) {
    return new ErrorResponse(errorCode, Map.of());
  }

  public static ErrorResponse of(CustomException e) {
    return new ErrorResponse(e.getResultCode(), e.getDetails());
  }

  public static ErrorResponse of(ResultCode errorCode, Map<String, Object> details) {
    return new ErrorResponse(errorCode, details == null ? Map.of() : details);
  }

  public static ErrorResponse of(ResultCode errorCode, Exception e) {
    return new ErrorResponse(errorCode, Map.of("exceptionType", e.getClass().getSimpleName()));
  }

  // 혹시 모를 500용
  public static ErrorResponse unexpected(Exception e) {
    return of(ResultCode.INTERNAL_SERVER_ERROR, e);
  }

}
