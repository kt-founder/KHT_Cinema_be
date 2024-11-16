package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.SeatRequest;
import system.system_cinema.DTO.Response.SeatResponse;
import system.system_cinema.Service.SeatService;

import java.util.List;

@RestController
@RequestMapping("/seats")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin("http://localhost:3000")
public class SeatController {

    final SeatService seatService;

    // **User APIs**
    @GetMapping("/get-by-room/{cinemaHallId}")
    public ApiResponse<List<SeatResponse>> getSeatsByRoom(@PathVariable String cinemaHallId) {
        try {
            return ApiResponse.<List<SeatResponse>>builder()
                    .message("Successful")
                    .data(seatService.getSeatsByCinemaHall(cinemaHallId))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<SeatResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    // **Admin APIs**
    @PostMapping("/admin/create")
    public ApiResponse<SeatResponse> createSeat(@RequestBody SeatRequest seatRequest) {
        try {
            return ApiResponse.<SeatResponse>builder()
                    .message("Seat created successfully")
                    .data(seatService.createSeat(seatRequest))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<SeatResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
