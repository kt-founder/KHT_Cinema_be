package system.system_cinema.Service;

import system.system_cinema.DTO.Request.SeatRequest;
import system.system_cinema.DTO.Response.SeatResponse;

import java.util.List;

public interface SeatService {

    List<SeatResponse> getSeatsByCinemaHall(String cinemaHallId);

    SeatResponse createSeat(SeatRequest request);

    SeatResponse updateSeat(String id, SeatRequest request);

    void deleteSeat(String id);
}
