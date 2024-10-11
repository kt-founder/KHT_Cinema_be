package system.system_cinema.Service;

import org.springframework.stereotype.Service;
import system.system_cinema.Model.Ticket;
import system.system_cinema.Model.User;

import java.util.Map;

@Service
public interface JwtService {
    String generateAccessToken(Map<String, Object> claims,String user);
    String generateRefreshToken(String user);
}
