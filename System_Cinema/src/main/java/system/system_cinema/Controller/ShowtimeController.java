package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.Request.ShowtimeRequest;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Response.ShowtimeResponse;
import system.system_cinema.Service.ShowtimeService;

@RestController
@RequestMapping("/showtimes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShowtimeController {
    final ShowtimeService showtimeService;

    @PostMapping("/create/{cinemaHallId}")
    public ApiResponse<ShowtimeResponse> createShowtime(@PathVariable String cinemaHallId, @RequestBody ShowtimeRequest showtimeRequest) {
        try {
            return ApiResponse.<ShowtimeResponse>builder()
                    .message("Showtime created successfully")
                    .data(showtimeService.createShowtime(cinemaHallId, showtimeRequest))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<ShowtimeResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
