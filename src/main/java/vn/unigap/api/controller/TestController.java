package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.model.ResponseWrapper;

@RestController
@Tag(name = "Health Check API")
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {
  private static final Logger logger = LoggerFactory.getLogger(TestController.class);

  @GetMapping("/healthCheck")
  public ResponseWrapper<String> healthCheck() {
    return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Health Check successfully", null);
  }

  @GetMapping("/checkSentry")
  public ResponseWrapper<String> checkSentry() {
    throw new RuntimeException("This is a test exception!");
  }
}
