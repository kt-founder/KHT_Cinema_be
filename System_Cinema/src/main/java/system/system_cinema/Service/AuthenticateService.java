package system.system_cinema.Service;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.EditUserRequest;
import system.system_cinema.DTO.Request.LoginRequest;
import system.system_cinema.DTO.Request.SignUpRequest;
import system.system_cinema.DTO.Response.OTP_Response;
import system.system_cinema.DTO.Response.TokenResponse;

import java.io.UnsupportedEncodingException;

@Service
public interface AuthenticateService {
    TokenResponse authenticate(LoginRequest loginRequest);
    TokenResponse authenticateAdmin(LoginRequest loginRequest);
    TokenResponse signUp(SignUpRequest signUpRequest);
    TokenResponse refreshToken(String refreshToken);
    OTP_Response createOTP(String email) throws MessagingException, UnsupportedEncodingException;
//    void UpdatePassword(EditUserRequest editUserRequest);
}
