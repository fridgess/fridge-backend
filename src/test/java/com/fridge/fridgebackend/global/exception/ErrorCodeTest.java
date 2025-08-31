package com.fridge.fridgebackend.global.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ErrorCodeTest {

  @Test
  @DisplayName("에러 코드가 중복되지 않아, 길이가 같다")
  void errorCodeShouldBeUnique() {
    Set<String> codes = Arrays.stream(ErrorCode.values())
        .map(ErrorCode::getCode)
        .collect(Collectors.toSet());

    assertThat(ErrorCode.values().length).isEqualTo(codes.size());
  }

  @Test
  @DisplayName("에러 코드의 코드 형식이 올바르다")
  void errorCodeShouldFollowNamingConvention() {
    for (ErrorCode errorCode : ErrorCode.values()) {
      assertThat(errorCode.getCode().matches("^[A-Z]{3,4}\\d{5}$")).isTrue();
    }
  }

  @Test
  @DisplayName("에러 코드의 코드 숫자 부분 앞 3자리가 HTTP 상태 코드와 일치한다")
  void httpStatusShouldMatchCodePrefix() {
    for (ErrorCode errorCode : ErrorCode.values()) {
      String code = errorCode.getCode();
      int httpStatus = errorCode.getStatus().value();

      assertThat(code).as("code=%s, status=%d", code, httpStatus)
          .matches("^[A-Z]{3,4}" + httpStatus + "\\d{2}$");
    }
  }

}