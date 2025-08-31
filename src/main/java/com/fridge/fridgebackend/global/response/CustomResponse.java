package com.fridge.fridgebackend.global.response;

import lombok.Getter;

@Getter
public class CustomResponse {

  private final boolean isSuccess;
  private final String code;
  private final String message;

  public CustomResponse(ResultCode code, boolean isSuccess) {
    this.isSuccess = isSuccess;
    this.code = code.getCode();
    this.message = code.getMessage();
  }
}
