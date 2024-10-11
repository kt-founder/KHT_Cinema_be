package system.system_cinema.Service.ServiceImplement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.EditUserRequest;
import system.system_cinema.DTO.Response.UserResponse;
import system.system_cinema.Mapper.UserMapper;
import system.system_cinema.Model.User;
import system.system_cinema.Repository.UserRepository;
import system.system_cinema.Service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public UserResponse GetUserDetails() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByName(username);
    }

    @Override
    public List<UserResponse> GetAllUsers() {
        return userRepository.findUsers();
    }

    @Override
    public void EditUser(EditUserRequest editUserRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(editUserRequest.getId()).orElseThrow(()->new RuntimeException("Not found user"));
        if (!user.getUsername().equals(username)) {
            throw new RuntimeException("Jwt not match information user");
        }
        userRepository.save(userMapper.update(editUserRequest, user));
    }

}
