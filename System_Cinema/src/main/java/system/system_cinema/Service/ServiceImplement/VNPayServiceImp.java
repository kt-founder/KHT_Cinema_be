package system.system_cinema.Service.ServiceImplement;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import system.system_cinema.Config.VNPAYConfig;
import system.system_cinema.DTO.Request.DetailsFvB;
import system.system_cinema.DTO.Request.LockSeatsRequest;
import system.system_cinema.Model.FoodBeverageOrder;
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
    private final SnackRepository snackRepository;
    private final ComboRepository comboRepository;

    @Override
    @Transactional
    public String CreateVNPayPayment(HttpServletRequest request, @RequestBody LockSeatsRequest lockSeatsRequest) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
//        List<SeatBooking> existingSeats = seatBookingRepository.findBySeatIdInAndShowTimeId(lockSeatsRequest.getSeatIds(), lockSeatsRequest.getShowtimeId());
//        for (SeatBooking seat : existingSeats) {
//            if ("sold".equals(seat.getStatus())) {
//                throw new RuntimeException("Sold Seat is already held");
//            }
//        }
        HandleLock(lockSeatsRequest.getSeatIds(), lockSeatsRequest.getShowtimeId());

        List<SeatBooking> seatBookings = new ArrayList<>();
        for (String seatId : lockSeatsRequest.getSeatIds()) {
            SeatBooking seatBooking = SeatBooking
                    .builder()
                    .seat(seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found")))
                    .status("sold")
                    .build();
            seatBookings.add(seatBooking);
        }
        List<FoodBeverageOrder> foodBeverageOrders = new ArrayList<>();
        if (lockSeatsRequest.getSnack() != null){
            for (DetailsFvB d : lockSeatsRequest.getSnack()) {
                FoodBeverageOrder f = FoodBeverageOrder.builder()
                        .quantity(d.getQuantity())
                        .snack(snackRepository.findById(d.getId()).orElseThrow(() -> new RuntimeException("Snack not found")))
                        .build();
                foodBeverageOrders.add(f);
            }
        }
        if (lockSeatsRequest.getCombo() != null){
            for (DetailsFvB d : lockSeatsRequest.getCombo()) {
                FoodBeverageOrder f = FoodBeverageOrder.builder()
                        .quantity(d.getQuantity())
                        .combo(comboRepository.findById(d.getId()).orElseThrow(()->new RuntimeException("Combo not found")))
                        .build();
                foodBeverageOrders.add(f);
            }
        }
        String idTicket = VNPayUtil.getRandomNumber(8);
        Ticket ticket = Ticket
                .builder()
                .id(idTicket)
                .user(userRepository.findByUsername(lockSeatsRequest.getUserId()).orElseThrow(() -> new RuntimeException("User not found")))
                .showtime(showTimeRepository.findById(lockSeatsRequest.getShowtimeId()).orElseThrow(() -> new RuntimeException("Showtime not found")))
                .price(Integer.parseInt(request.getParameter("amount")))
                .seatBookings(seatBookings)
                .build();
        if (!foodBeverageOrders.isEmpty()) {
            ticket.setFoodBeverageOrders(foodBeverageOrders);
            for (FoodBeverageOrder fb : foodBeverageOrders) {
                fb.setTicket(ticket);
            }
        }
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

    @Transactional
    public void HandleLock (List<String> v1, String v2){
        List<SeatBooking> existingSeats = seatBookingRepository.findBySeatIdInAndShowTimeId(v1, v2);
        for (SeatBooking seat : existingSeats) {
            if ("sold".equals(seat.getStatus())) {
                throw new RuntimeException("Sold Seat is already held");
            }
        }
    }
}
