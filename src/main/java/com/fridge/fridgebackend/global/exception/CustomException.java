package com.fridge.fridgebackend.global.exception;

import com.fridge.fridgebackend.global.response.ResultCode;
import java.util.Map;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

  private final ResultCode resultCode;
  private final Map<String, Object> details;

  public CustomException(ResultCode resultCode) {
    super(resultCode.getMessage());
    this.resultCode = resultCode;
    this.details = Map.of();
  }

  public CustomException(ResultCode resultCode, Map<String, Object> details) {
    super(resultCode.getMessage());
    this.resultCode = resultCode;
    this.details = details;
  }

}
