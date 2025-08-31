package com.fridge.fridgebackend.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 결과 코드 작성
 * <p>
 * 성공 코드 및 특정 도메인에서 발생할 수 있는 에러 등 응답 시 전달해야 하는 결과 코드 정의
 */

@Getter
@RequiredArgsConstructor
public enum ResultCode {

  // SUCCESS
  SUCCESS(HttpStatus.OK, "SUC20000", "OK"),
  CREATED(HttpStatus.CREATED, "SUC20101", "CREATED"),
  DELETE_SUCCESS(HttpStatus.NO_CONTENT, "SUC20401", "DELETE SUCCESS"),

  // ERROR -------------------
  // COMMON
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMM50000", "서버 에러입니다. 관리자에게 연락해주세요."),
  SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "COMM50301", "일시적으로 사용할 수 없습니다."),
  BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "COMM50202", "외부 서비스 오류입니다."),
  GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "COMM50403", "외부 서비스 응답 지연입니다."),

  // AUTH
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH40100", "인증이 필요합니다."),
  ACCESS_DENIED(HttpStatus.FORBIDDEN, "AUTH40301", "권한이 없습니다."),
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH40102", "토큰이 유효하지 않습니다."),
  EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH40103", "토큰이 만료되었습니다."),

  // REQUEST
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "REQ40000", "잘못된 요청입니다."),
  MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "REQ40001", "필수 파라미터가 누락되었습니다."),
  TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "REQ40002", "파라미터 타입이 올바르지 않습니다."),
  JSON_PARSE_ERROR(HttpStatus.BAD_REQUEST, "REQ40003", "요청 본문을 해석할 수 없습니다."),
  REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "REQ40804", "요청 시간이 초과되었습니다."),
  METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "REQ40505", "허용되지 않은 메서드입니다."),
  UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "REQ41506", "지원하지 않는 콘텐츠 타입입니다."),
  PAYLOAD_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "REQ41307", "업로드 용량을 초과했습니다."),
  TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "REQ42908", "요청이 너무 많습니다."),

  // RESOURCE
  NOT_FOUND(HttpStatus.NOT_FOUND, "RES40400", "리소스를 찾을 수 없습니다."),
  DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "RES40901", "이미 존재하는 리소스입니다."),
  STATE_CONFLICT(HttpStatus.CONFLICT, "RES40902", "요청이 현재 상태와 충돌합니다."),
  PRECONDITION_FAILED(HttpStatus.PRECONDITION_FAILED, "RES41203", "사전 조건이 충족되지 않았습니다."),
  BUSINESS_RULE_VIOLATION(HttpStatus.UNPROCESSABLE_ENTITY, "RES42204", "비즈니스 규칙 위반입니다."),

  // DATA
  DATA_INTEGRITY_VIOLATION(HttpStatus.CONFLICT, "DATA40900", "데이터 무결성 위반입니다."),
  UNIQUE_CONSTRAINT_VIOLATION(HttpStatus.CONFLICT, "DATA40901", "중복 키가 존재합니다."),
  FOREIGN_KEY_VIOLATION(HttpStatus.CONFLICT, "DATA40902", "참조 무결성 위반입니다."),
  OPTIMISTIC_LOCK_FAILURE(HttpStatus.PRECONDITION_FAILED, "DATA41203", "동시 수정 충돌입니다.");

  // DOMAIN - 도메인 개발 시 생기는 오류는 이하에 존재

  private final HttpStatus status;
  private final String code;
  private final String message;

}
