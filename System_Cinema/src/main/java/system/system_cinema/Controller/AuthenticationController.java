package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.LoginRequest;
import system.system_cinema.DTO.Request.SignUpRequest;
import system.system_cinema.DTO.Response.TokenResponse;
import system.system_cinema.Service.ServiceImplement.AuthenticateServiceImp;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticateServiceImp authenticateServiceImp;

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            return ApiResponse.<TokenResponse>builder()
                    .message("Successful")
                    .data(authenticateServiceImp.authenticate(loginRequest))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<TokenResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/sign-up")
    public ApiResponse<TokenResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            return ApiResponse.<TokenResponse>builder()
                    .message("Successful")
                    .data(authenticateServiceImp.signUp(signUpRequest))
                    .build();
        } catch (Exception e){
            return ApiResponse.<TokenResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/refresh-token")
    public ApiResponse<TokenResponse> refreshToken(@RequestBody String refreshToken) {
        try {
            return ApiResponse.<TokenResponse>builder()
                    .message("Successful")
                    .data(authenticateServiceImp.refreshToken(refreshToken))
                    .build();
        } catch (Exception e){
            return ApiResponse.<TokenResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
