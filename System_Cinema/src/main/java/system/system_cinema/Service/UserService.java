package system.system_cinema.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.EditUserRequest;
import system.system_cinema.DTO.Response.UserResponse;

import java.util.List;

@Service
public interface UserService {
    UserDetailsService userDetailsService();
    UserResponse GetUserDetails();
    List<UserResponse> GetAllUsers();
    void EditUser(EditUserRequest editUserRequest);
}
