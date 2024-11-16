package system.system_cinema.Service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

public interface VNPayService {
    public String CreateVNPayPayment(HttpServletRequest request);
}
