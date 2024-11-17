package system.system_cinema.Service.ServiceImplement;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import system.system_cinema.Config.VNPAYConfig;
import system.system_cinema.DTO.Request.LockSeatsRequest;
import system.system_cinema.Model.SeatBooking;
import system.system_cinema.Model.Ticket;
import system.system_cinema.Repository.*;
import system.system_cinema.Service.VNPayService;
import system.system_cinema.Utils.VNPayUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class VNPayServiceImp implements VNPayService {
    SeatBookingRepository seatBookingRepository;
    VNPAYConfig vnPayConfig;
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;
    private final ShowTimeRepository showTimeRepository;
    private final UserRepository userRepository;

    @Override
    public String CreateVNPayPayment(HttpServletRequest request, @RequestBody LockSeatsRequest lockSeatsRequest) {
        System.out.println(lockSeatsRequest.getSeatIds().size());
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        System.out.println("1");
        List<SeatBooking> existingSeats = seatBookingRepository.findBySeatIdInAndShowTimeId(lockSeatsRequest.getSeatIds(), lockSeatsRequest.getShowtimeId());
        System.out.println("2");
        for (SeatBooking seat : existingSeats) {
            if ("sold".equals(seat.getStatus())) {
                throw new RuntimeException("Sold Seat is already held");
            }
        }
        System.out.println("3");
        List<SeatBooking> seatBookings = new ArrayList<>();
        for (String seatId : lockSeatsRequest.getSeatIds()) {
            SeatBooking seatBooking = SeatBooking
                    .builder()
                    .seat(seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found")))
                    .status("sold")
                    .build();
            seatBookings.add(seatBooking);
        }

        String idTicket = VNPayUtil.getRandomNumber(8);
        Ticket ticket = Ticket
                .builder()
                .id(idTicket)
                .user(userRepository.findByUsername(lockSeatsRequest.getUserId()).orElseThrow(() -> new RuntimeException("User not found")))
                .showtime(showTimeRepository.findById(lockSeatsRequest.getShowtimeId()).orElseThrow(() -> new RuntimeException("Showtime not found")))
                .price((int) amount)
                .seatBookings(seatBookings) // Gắn danh sách SeatBooking vào Ticket
                .build();

        for (SeatBooking seatBooking : seatBookings) {
            seatBooking.setTicket(ticket);
        }

        bookingRepository.save(ticket);

        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig(idTicket);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        return vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
    }
}
