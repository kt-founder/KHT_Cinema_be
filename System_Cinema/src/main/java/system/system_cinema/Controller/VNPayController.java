package system.system_cinema.Controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.Service.VNPayService;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VNPayController {
    VNPayService service;
    @GetMapping("/vn-pay")
    public ApiResponse<?> pay(HttpServletRequest request) {
        return ApiResponse
                .builder()
                .data(service.CreateVNPayPayment(request))
                .build();
    }
    @GetMapping("/vn-pay-callback")
    public ApiResponse<?> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            return ApiResponse.builder()
                    .message("Payment successful")
                    .build();
        } else {
            return ApiResponse.builder()
                    .message("Payment failed")
                    .build();
        }
    }
}
