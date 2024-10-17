package system.system_cinema.Service;

import system.system_cinema.DTO.Request.ShowtimeRequest;
import system.system_cinema.DTO.Response.ShowtimeResponse;

public interface ShowtimeService {
    ShowtimeResponse createShowtime(String cinemaHallId, ShowtimeRequest showtimeRequest);
}
