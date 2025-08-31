package com.fridge.fridgebackend.global.response;

import lombok.Getter;

@Getter
public class DataResponse<T> extends CustomResponse {

  private final T data;

  private DataResponse(ResultCode code, T data) {
    super(code, true);
    this.data = data;
  }

  public static <T> DataResponse<T> from(ResultCode code, T data) {
    return new DataResponse<>(code, data);
  }

  public static <T> DataResponse<T> ok(T data) {
    return new DataResponse<>(ResultCode.SUCCESS, data);
  }

}
