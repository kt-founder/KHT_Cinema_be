package system.system_cinema.Service;

import system.system_cinema.DTO.Request.SeatBookingRequest;
import system.system_cinema.DTO.Response.SeatBookingResponse;

import java.util.List;

public interface SeatBookingService {
    boolean lockSeats(List<String> seatIds, String showtimeId, String userId);
}
