package system.system_cinema.Service.ServiceImplement;

import io.jsonwebtoken.Claims;
import jakarta.mail.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.EditUserRequest;
import system.system_cinema.DTO.Request.LoginRequest;
import system.system_cinema.DTO.Request.SignUpRequest;
import system.system_cinema.DTO.Request.VerifyRequest;
import system.system_cinema.DTO.Response.OTP_Response;
import system.system_cinema.DTO.Response.TokenResponse;
import system.system_cinema.Model.Role;
import system.system_cinema.Model.User;
import system.system_cinema.Repository.RoleRepository;
import system.system_cinema.Repository.UserRepository;
import system.system_cinema.Service.AuthenticateService;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticateServiceImp implements AuthenticateService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    JwtServiceImp jwtService;
    private final RoleRepository roleRepository;
    PasswordEncoder getPasswordEncoder;
    MailService mailService;

    @Override
    public TokenResponse authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        var user = userRepository.findUser(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(loginRequest.getUsername() + " not found"));
        if (!user.isActive()){
            throw new UsernameNotFoundException("Account has not been activated");
        }
        return TokenResponse
                .builder()
                .access_token(jwtService.generateAccessToken(getClaims(user), user.getUsername()))
                .refresh_token(jwtService.generateRefreshToken(user.getUsername()))
                .build();
    }

    @Override
    public TokenResponse authenticateAdmin(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        var user = userRepository.findAdmin(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(loginRequest.getUsername() + " not found"));
        if (!user.isActive()){
            throw new UsernameNotFoundException("Account has not been activated");
        }
        return TokenResponse
                .builder()
                .access_token(jwtService.generateAccessToken(getClaims(user), user.getUsername()))
                .refresh_token(jwtService.generateRefreshToken(user.getUsername()))
                .build();
    }

    @Override
    public TokenResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername()) && userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email or Username already exists");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("USER"));
        User user = User
                .builder()
                .username(signUpRequest.getUsername())
                .password(getPasswordEncoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .dateCreate(LocalDateTime.now())
                .isActive(true)
                .roles(roles)
                .build();
        userRepository.save(user);
        return TokenResponse
                .builder()
                .access_token(jwtService.generateAccessToken(getClaims(user), user.getUsername()))
                .refresh_token(jwtService.generateRefreshToken(user.getUsername()))
                .build();
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        Date expiration = jwtService.extractClaim(refreshToken, Claims::getExpiration);
        if (expiration == null || expiration.before(new Date(System.currentTimeMillis()))) {
            throw new RuntimeException("Refresh token expired");
        }
        String username = jwtService.extractClaim(refreshToken, Claims::getSubject);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return TokenResponse
                .builder()
                .access_token(jwtService.generateAccessToken(getClaims(user), user.getUsername()))
                .build();
    }

    @Override
    public OTP_Response createOTP(VerifyRequest verifyRequest) throws MessagingException, UnsupportedEncodingException {
        String id = userRepository.findByEmailAndUsername(verifyRequest.getEmail(), verifyRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Not found user") ).getId();
        String code = mailService.sendEmail(verifyRequest.getEmail());
        return OTP_Response
                .builder()
                .code(code)
                .expiration(LocalDateTime.now())
                .id(id)
                .build();
    }
//    @Override
//    public void UpdatePassword(EditUserRequest editUserRequest) {
//        User user = userRepository.findByEmail(editUserRequest.getEmail()).orElseThrow(()->new RuntimeException("Not found user"));
//        user.setPassword(getPasswordEncoder.encode(editUserRequest.getPassword()));
//        userRepository.save(user);
//    }

    private Map<String, Object> getClaims(User user) {
        Map<String, Object> map = new HashMap<>();
        StringBuilder s = new StringBuilder();
        for (var role : user.getRoles()) {
//            map.put("role", role.getName());
            s.append(role.getName()).append(" ");
        }
        map.put("role", s);
        return map;
    }
}
