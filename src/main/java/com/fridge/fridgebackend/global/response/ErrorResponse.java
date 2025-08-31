package com.fridge.fridgebackend.global.response;

import com.fridge.fridgebackend.global.exception.ErrorCode;
import java.util.Map;
import lombok.Builder;

/**
 * 에러 응답 시 사용하는 응답 record.
 *
 * @param exceptionName
 * @param message       메시지 (어떤 에러가 발생했는지)
 * @param code          에러 코드
 * @param details       해당 에러에 대한 세부 사항
 */
@Builder
public record ErrorResponse(
    String exceptionName,
    String message,
    String code,
    Map<String, Object> details
) {

  public static ErrorResponse of(ErrorCode errorCode, Exception e, Map<String, Object> details) {
    return ErrorResponse.builder()
        .exceptionName(e.getClass().getSimpleName())
        .code(errorCode.getCode())
        .message(errorCode.getMessage())
        .details(details)
        .build();
  }
}
