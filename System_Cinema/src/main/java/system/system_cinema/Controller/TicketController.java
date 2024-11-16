package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.TicketRequest;
import system.system_cinema.DTO.Response.TicketResponse;
import system.system_cinema.Service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin("http://localhost:3000")
public class TicketController {

    final TicketService ticketService;

    // **User APIs**
    @PostMapping("/create")
    public ApiResponse<TicketResponse> createTicket(@RequestBody TicketRequest ticketRequest) {
        try {
            return ApiResponse.<TicketResponse>builder()
                    .message("Ticket created successfully")
                    .data(ticketService.createTicket(ticketRequest))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<TicketResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/get-by-user/{userId}")
    public ApiResponse<List<TicketResponse>> getTicketsByUser(@PathVariable String userId) {
        try {
            return ApiResponse.<List<TicketResponse>>builder()
                    .message("Successful")
                    .data(ticketService.getTicketsByUser(userId))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<TicketResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
