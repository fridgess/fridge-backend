package com.fridge.fridgebackend.global.response;

import com.fridge.fridgebackend.global.exception.CustomException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.net.URI;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/res_test")
public class TestController {

  record CreateRequest(@NotBlank String name, @Min(1) int count) {

  }

  record TestResponse(String name, int count) {

  }

  @PostMapping("/validate")
  public DataResponse<TestResponse> validate(@Valid @RequestBody CreateRequest request) {
    return DataResponse.ok(new TestResponse(request.name(), request.count()));
  }

  @GetMapping("/{id}")
  public DataResponse<TestResponse> uuid(@PathVariable UUID id) {
    return DataResponse.ok(new TestResponse(id.toString(), 6));
  }

  @PostMapping("/create")
  public ResponseEntity<DataResponse<TestResponse>> create(
      @Valid @RequestBody CreateRequest request) {

    String id = UUID.randomUUID().toString();

    URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()  // /res_test/create
        .replacePath("/res_test/{id}").buildAndExpand(id).toUri();

    return ResponseEntity.created(location)
        .body(DataResponse.ok(new TestResponse(request.name(), request.count())));
  }

  @GetMapping("/custom_exception")
  public void customException() {
    throw new CustomException(ResultCode.DUPLICATE_RESOURCE, Map.of("resource", "중복된 이름"));
  }

  @GetMapping("/runtime_exception")
  public void runtimeException() {
    throw new RuntimeException("RUNTIME");
  }

}
