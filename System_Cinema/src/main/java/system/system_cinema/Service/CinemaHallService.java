package system.system_cinema.Service;

import system.system_cinema.DTO.Request.CinemaHallRequest;
import system.system_cinema.DTO.Request.ShowtimeRequest;
import system.system_cinema.DTO.Response.CinemaHallResponse;

import java.util.List;

public interface CinemaHallService {
    CinemaHallResponse getCinemaHallById(String id);
    List<CinemaHallResponse> getAllCinemaHalls();
    CinemaHallResponse changeCinemaHallStatus(String id, boolean isActive);
    CinemaHallResponse addShowtime(String cinemaHallId, ShowtimeRequest showtimeRequest);
}
