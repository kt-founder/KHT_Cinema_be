package system.system_cinema.Mapper;

import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Response.CinemaHallResponse;
import system.system_cinema.DTO.Response.ShowtimeResponse;
import system.system_cinema.Model.CinemaHall;
import system.system_cinema.Model.Showtime;

import java.util.stream.Collectors;

@Component
public class CinemaHallMapper {

    public CinemaHallResponse toCinemaHallResponse(CinemaHall cinemaHall) {
        return CinemaHallResponse.builder()
                .id(cinemaHall.getId())
                .name(cinemaHall.getName())
                .isActive(cinemaHall.isActive())
                .showtimes(cinemaHall.getShowtimes().stream()
                        .map(this::toShowtimeResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    private ShowtimeResponse toShowtimeResponse(Showtime showtime) {
        return ShowtimeResponse.builder()
                .id(showtime.getId())
                .movieTitle(showtime.getMovie().getTitle())
                .startTime(showtime.getStartTime())
                .endTime(showtime.getEndTime())
                .build();
    }
}
