package vn.unigap.api.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.input.SignUpRequest;
import vn.unigap.api.dto.input.SigninRequest;
import vn.unigap.api.dto.output.JwtAuthenticationResponse;
import vn.unigap.api.entity.Role;
import vn.unigap.api.entity.User;
import vn.unigap.api.repository.UserRepository;
import vn.unigap.api.service.jwt.JwtService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements  AuthenticationService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER).build();
        userRepository.save(user);
        Map<String,String> jwt = jwtService.generateTokens(user);
        return JwtAuthenticationResponse.builder().accessToken(jwt.get("accessToken")).refreshToken(jwt.get("refreshToken")).build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        Map<String,String> jwt = jwtService.generateTokens(user);
        return JwtAuthenticationResponse.builder().accessToken(jwt.get("accessToken")).refreshToken(jwt.get("refreshToken")).build();
    }
}
