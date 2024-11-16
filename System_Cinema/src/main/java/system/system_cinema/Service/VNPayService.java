package system.system_cinema.Service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.LockSeatsRequest;

public interface VNPayService {
    public String CreateVNPayPayment(HttpServletRequest request, LockSeatsRequest lockSeatsRequest);
}
