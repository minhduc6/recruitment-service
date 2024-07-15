package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.input.SignUpRequest;
import vn.unigap.api.dto.input.SigninRequest;
import vn.unigap.api.dto.output.JwtAuthenticationResponse;
import vn.unigap.api.model.ResponseWrapper;
import vn.unigap.api.service.auth.AuthenticationService;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth API")
@AllArgsConstructor
public class AuthController {
  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseWrapper<JwtAuthenticationResponse> login(
      @Valid @RequestBody SigninRequest signinRequest) {
    logger.info("Received request POST /auth/login . Payload: {}", signinRequest);
    JwtAuthenticationResponse response = authenticationService.signIn(signinRequest);
    logger.info("Returning response POST /auth/login  Login successfully");
    return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Login successfully", response);
  }

  @PostMapping("/register")
  public ResponseWrapper<JwtAuthenticationResponse> register(
      @Valid @RequestBody SignUpRequest signUpRequest) {
    logger.info("Received request POST /auth/register . Payload: {}", signUpRequest);
    JwtAuthenticationResponse response = authenticationService.signUp(signUpRequest);
    logger.info("Returning response POST /auth/register  Register Success successfully");
    return new ResponseWrapper<>(
        0, HttpStatus.OK.value(), "Register Success successfully", response);
  }
}
