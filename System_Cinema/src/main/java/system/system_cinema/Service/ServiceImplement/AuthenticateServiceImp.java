package system.system_cinema.Service.ServiceImplement;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.LoginRequest;
import system.system_cinema.DTO.Request.SignUpRequest;
import system.system_cinema.DTO.Response.TokenResponse;
import system.system_cinema.Model.Role;
import system.system_cinema.Model.User;
import system.system_cinema.Repository.RoleRepository;
import system.system_cinema.Repository.UserRepository;
import system.system_cinema.Service.AuthenticateService;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticateServiceImp implements AuthenticateService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    JwtServiceImp jwtService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder getPasswordEncoder;

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

    private Map<String, Object> getClaims(User user) {
        Map<String, Object> map = new HashMap<>();
        for (var role : user.getRoles()) {
            map.put("role", role.getName());
        }
        return map;
    }
}
