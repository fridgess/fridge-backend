package com.fridge.fridgebackend.global.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResultCodeTest {

  @Test
  @DisplayName("결과 코드가 중복되지 않아, 길이가 같다")
  void errorCodeShouldBeUnique() {
    Set<String> codes = Arrays.stream(ResultCode.values())
        .map(ResultCode::getCode)
        .collect(Collectors.toSet());

    assertThat(ResultCode.values().length).isEqualTo(codes.size());
  }

  @Test
  @DisplayName("결과 코드의 코드 형식이 올바르다")
  void errorCodeShouldFollowNamingConvention() {
    for (ResultCode resultCode : ResultCode.values()) {
      assertThat(resultCode.getCode().matches("^[A-Z]{3,4}\\d{5}$")).isTrue();
    }
  }

  @Test
  @DisplayName("결과 코드의 코드 숫자 부분 앞 3자리가 HTTP 상태 코드와 일치한다")
  void httpStatusShouldMatchCodePrefix() {
    for (ResultCode resultCode : ResultCode.values()) {
      String code = resultCode.getCode();
      int httpStatus = resultCode.getStatus().value();

      assertThat(code).as("code=%s, status=%d", code, httpStatus)
          .matches("^[A-Z]{3,4}" + httpStatus + "\\d{2}$");
    }
  }

}