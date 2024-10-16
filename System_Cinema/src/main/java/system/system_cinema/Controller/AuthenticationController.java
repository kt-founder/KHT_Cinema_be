package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.EditUserRequest;
import system.system_cinema.DTO.Request.LoginRequest;
import system.system_cinema.DTO.Request.OneFieldRequest;
import system.system_cinema.DTO.Request.SignUpRequest;
import system.system_cinema.DTO.Response.OTP_Response;
import system.system_cinema.DTO.Response.TokenResponse;
import system.system_cinema.Service.ServiceImplement.AuthenticateServiceImp;
import system.system_cinema.Service.ServiceImplement.UserServiceImp;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticateServiceImp authenticateServiceImp;
    UserServiceImp userServiceImp;
    @PostMapping("/login-user")
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

    @PostMapping("/login-admin")
    public ApiResponse<TokenResponse> loginAdmin(@RequestBody LoginRequest loginRequest) {
        try {
            return ApiResponse.<TokenResponse>builder()
                    .message("Successful")
                    .data(authenticateServiceImp.authenticateAdmin(loginRequest))
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
    public ApiResponse<TokenResponse> refreshToken(@RequestBody OneFieldRequest oneFieldRequest) {
        try {
            return ApiResponse.<TokenResponse>builder()
                    .message("Successful")
                    .data(authenticateServiceImp.refreshToken(oneFieldRequest.getInput()))
                    .build();
        } catch (Exception e){
            return ApiResponse.<TokenResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
    @PostMapping("forgot-password")
    public ApiResponse<OTP_Response> forgotPassword(@RequestBody OneFieldRequest oneFieldRequest) {
        System.out.println(oneFieldRequest.getInput());
        try {
            return ApiResponse.<OTP_Response>builder()
                    .data(authenticateServiceImp.createOTP(oneFieldRequest.getInput()))
                    .build();
        } catch (Exception e){
            return ApiResponse.<OTP_Response>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
    @PatchMapping("/update-password")
    public ApiResponse<?> updatePassword(@RequestBody EditUserRequest editUserRequest) {
        try {
//            authenticateServiceImp.UpdatePassword(editUserRequest);
            userServiceImp.UpdatePassword(editUserRequest);
            return ApiResponse.builder()
                    .message("Successful")
                    .build();
        } catch (Exception e){
            return ApiResponse.builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
