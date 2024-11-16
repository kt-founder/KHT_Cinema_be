package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.SeatBookingRequest;
import system.system_cinema.DTO.Response.SeatBookingResponse;
import system.system_cinema.Service.SeatBookingService;

import java.util.List;

@RestController
@RequestMapping("/seat-bookings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin("http://localhost:3000")
public class SeatBookingController {

    final SeatBookingService seatBookingService;

    // **User APIs**
    @PostMapping("/create")
    public ApiResponse<SeatBookingResponse> createSeatBooking(@RequestBody SeatBookingRequest seatBookingRequest) {
        try {
            return ApiResponse.<SeatBookingResponse>builder()
                    .message("Seat booked successfully")
                    .data(seatBookingService.createSeatBooking(seatBookingRequest))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<SeatBookingResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/get-by-ticket/{ticketId}")
    public ApiResponse<List<SeatBookingResponse>> getSeatBookingsByTicket(@PathVariable String ticketId) {
        try {
            return ApiResponse.<List<SeatBookingResponse>>builder()
                    .message("Successful")
                    .data(seatBookingService.getSeatBookingsByTicket(ticketId))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<SeatBookingResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
