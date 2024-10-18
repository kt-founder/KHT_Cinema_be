package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.Request.CinemaHallRequest;
import system.system_cinema.DTO.Request.ShowtimeRequest;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Response.CinemaHallResponse;
import system.system_cinema.Service.CinemaHallService;

import java.util.List;

@RestController
@RequestMapping("/cinema-halls")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CinemaHallController {
    final CinemaHallService cinemaHallService;

    @GetMapping("/get/{id}")
    public ApiResponse<CinemaHallResponse> getCinemaHallById(@PathVariable String id) {
        try {
            return ApiResponse.<CinemaHallResponse>builder()
                    .message("Successful")
                    .data(cinemaHallService.getCinemaHallById(id))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<CinemaHallResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/get-all")
    public ApiResponse<List<CinemaHallResponse>> getAllCinemaHalls() {
        try {
            return ApiResponse.<List<CinemaHallResponse>>builder()
                    .message("Successful")
                    .data(cinemaHallService.getAllCinemaHalls())
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<CinemaHallResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PutMapping("/change-status/{id}")
    public ApiResponse<CinemaHallResponse> changeCinemaHallStatus(@PathVariable String id, @RequestParam boolean isActive) {
        try {
            return ApiResponse.<CinemaHallResponse>builder()
                    .message("Status changed successfully")
                    .data(cinemaHallService.changeCinemaHallStatus(id, isActive))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<CinemaHallResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/add-showtime/{cinemaHallId}")
    public ApiResponse<CinemaHallResponse> addShowtime(@PathVariable String cinemaHallId, @RequestBody ShowtimeRequest showtimeRequest) {
        try {
            return ApiResponse.<CinemaHallResponse>builder()
                    .message("Showtime added successfully")
                    .data(cinemaHallService.addShowtime(cinemaHallId, showtimeRequest))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<CinemaHallResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
