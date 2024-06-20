package vn.unigap.api.service.auth;

import vn.unigap.api.dto.input.SignUpRequest;
import vn.unigap.api.dto.input.SigninRequest;
import vn.unigap.api.dto.output.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SigninRequest request);
}