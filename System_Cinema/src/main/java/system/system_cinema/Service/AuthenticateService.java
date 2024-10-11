package system.system_cinema.Service;

import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.LoginRequest;
import system.system_cinema.DTO.Request.SignUpRequest;
import system.system_cinema.DTO.Response.TokenResponse;

@Service
public interface AuthenticateService {
    TokenResponse authenticate(LoginRequest loginRequest);
    TokenResponse signUp(SignUpRequest signUpRequest);
    TokenResponse refreshToken(String refreshToken);
}
