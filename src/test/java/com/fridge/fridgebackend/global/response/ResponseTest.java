package com.fridge.fridgebackend.global.response;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fridge.fridgebackend.global.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = TestController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false) // 보안 필터 끄기
public class ResponseTest {

  @Autowired
  MockMvc mvc;

  @Test
  @DisplayName("유효성 검사에서 400 예외가 발생한다")
  void validation_failed_204() throws Exception {
    // given
    String body = """
        {
          "name":"",
          "count": 0
        }
        """;

    ResultCode target = ResultCode.INVALID_INPUT_VALUE;

    mvc.perform(post("/res_test/validate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().is(target.getStatus().value()))
        .andExpect(jsonPath("$.code").value(target.getCode()))
        .andExpect(jsonPath("$.message").value(target.getMessage()))
        .andExpect(jsonPath("$.details.validationError").isArray())
        .andExpect(jsonPath("$.details.validationError[0]").isString());
  }

  @Test
  @DisplayName("생성 시, 201로 성공적 응답이 전달된다")
  void create_success_201() throws Exception {

    String body = """
        {
          "name":"a",
          "count": 6
        }
        """;

    mvc.perform(post("/res_test/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", containsString("/res_test/")));
  }

  @Test
  @DisplayName("커스텀 예외가 발생한다")
  void throw_custom_exception() throws Exception {
    ResultCode target = ResultCode.DUPLICATE_RESOURCE;

    mvc.perform(get("/res_test/custom_exception"))
        .andExpect(status().is(target.getStatus().value()))
        .andExpect(jsonPath("$.code").value(target.getCode()))
        .andExpect(jsonPath("$.details.resource").value("중복된 이름"));
  }

  @Test
  @DisplayName("그 외의 예외가 발생하여 500에러가 발생한다")
  void throw_other_runtime_exception_500() throws Exception {
    ResultCode target = ResultCode.INTERNAL_SERVER_ERROR;

    mvc.perform(get("/res_test/runtime_exception"))
        .andExpect(status().is(target.getStatus().value()))
        .andExpect(jsonPath("$.code").value(target.getCode()));
  }
}
