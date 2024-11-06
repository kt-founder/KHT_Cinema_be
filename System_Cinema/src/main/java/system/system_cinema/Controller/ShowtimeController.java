package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.Request.ShowTimeRequestCreate;
import system.system_cinema.DTO.Request.ShowtimeRequest;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Response.ShowtimeResponse;
import system.system_cinema.Service.ShowTimeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/showtimes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShowtimeController {
    final ShowTimeService showtimeService;

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
//    For admin
    @PostMapping("/admin/create")
    public ApiResponse<?> CreateShowTimeCtr(@RequestBody ShowTimeRequestCreate requestCreate){
        try {
            showtimeService.createShowTime(requestCreate);
            return ApiResponse.<ShowtimeResponse>builder()
                    .message("Showtime created successfully")
                    .build();
        } catch (Exception e) {
            return ApiResponse.<ShowtimeResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
    @PatchMapping("/admin/update")
    public ApiResponse<?> UpdateShowTimeCtr(@RequestParam String showTimeId, @RequestParam String roomId){
        try {
            showtimeService.updateShowTime(showTimeId, roomId);
            return ApiResponse.<ShowtimeResponse>builder()
                    .message("Showtime update successfully")
                    .build();
        } catch (Exception e) {
            return ApiResponse.<ShowtimeResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
    @GetMapping("/admin/get-list")
    public ApiResponse<?> GetShowTimeCtr(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date
    ) {
        System.out.println(date);
        try {
            return ApiResponse.<List<?>>builder()
                    .data(showtimeService.getListShowTime(date))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<ShowtimeResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/admin/get-all-showtime")
    public ApiResponse<?> GetAll(
    ) {
        try {
            return ApiResponse.<List<?>>builder()
                    .data(showtimeService.getAllShowTimes())
                    .build();
        } catch (Exception e) {
            return ApiResponse.<ShowtimeResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
    @GetMapping("/admin/delete")
    public ApiResponse<?> DeleteShowTimeCtr(@RequestParam String showTimeId){
        try {
            showtimeService.deleteShowTime(showTimeId);
            return ApiResponse.<ShowtimeResponse>builder()
                    .message("Showtime delete successfully")
                    .build();
        } catch (Exception e) {
            return ApiResponse.<ShowtimeResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
//    For user
}
