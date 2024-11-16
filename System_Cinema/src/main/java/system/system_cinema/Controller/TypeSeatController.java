package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.TypeSeatRequest;
import system.system_cinema.DTO.Response.TypeSeatResponse;
import system.system_cinema.Service.TypeSeatService;

import java.util.List;

@RestController
@RequestMapping("/type-seats")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin("http://localhost:3000")
public class TypeSeatController {

    final TypeSeatService typeSeatService;

    // **User APIs**
    @GetMapping("/get-all")
    public ApiResponse<List<TypeSeatResponse>> getAllTypeSeats() {
        try {
            return ApiResponse.<List<TypeSeatResponse>>builder()
                    .message("Successful")
                    .data(typeSeatService.getAllTypeSeats())
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<TypeSeatResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    // **Admin APIs**
    @PostMapping("/admin/create")
    public ApiResponse<TypeSeatResponse> createTypeSeat(@RequestBody TypeSeatRequest typeSeatRequest) {
        try {
            return ApiResponse.<TypeSeatResponse>builder()
                    .message("Type Seat created successfully")
                    .data(typeSeatService.createTypeSeat(typeSeatRequest))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<TypeSeatResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
