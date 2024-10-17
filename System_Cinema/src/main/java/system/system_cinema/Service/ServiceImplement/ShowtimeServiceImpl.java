package system.system_cinema.Service.ServiceImplement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.ShowtimeRequest;
import system.system_cinema.DTO.Response.ShowtimeResponse;
import system.system_cinema.Mapper.ShowtimeMapper;
import system.system_cinema.Model.CinemaHall;
import system.system_cinema.Model.Movie;
import system.system_cinema.Model.Showtime;
import system.system_cinema.Repository.CinemaHallRepository;
import system.system_cinema.Repository.MovieRepository;
import system.system_cinema.Repository.ShowtimeRepository;
import system.system_cinema.Service.ShowtimeService;

@Service
@RequiredArgsConstructor
public class ShowtimeServiceImpl implements ShowtimeService {

    private final CinemaHallRepository cinemaHallRepository;
    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;
    private final ShowtimeMapper showtimeMapper;

    @Override
    public ShowtimeResponse createShowtime(String cinemaHallId, ShowtimeRequest showtimeRequest) {
        CinemaHall cinemaHall = cinemaHallRepository.findById(cinemaHallId)
                .orElseThrow(() -> new RuntimeException("Cinema Hall not found"));

        Movie movie = movieRepository.findById(showtimeRequest.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Showtime showtime = showtimeMapper.toShowtime(showtimeRequest, movie);
        showtime.setCinemaHall(cinemaHall);

        Showtime savedShowtime = showtimeRepository.save(showtime);

        return showtimeMapper.toShowtimeResponse(savedShowtime);
    }
}
