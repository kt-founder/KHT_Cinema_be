package system.system_cinema.Service.ServiceImplement;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.DetailShowTime;
import system.system_cinema.DTO.Request.ShowTimeRequestCreate;
import system.system_cinema.DTO.Request.ShowtimeRequest;
import system.system_cinema.DTO.Response.ShowtimeResponse;
import system.system_cinema.Mapper.ShowtimeMapper;
import system.system_cinema.Model.CinemaHall;
import system.system_cinema.Model.Movie;
import system.system_cinema.Model.Showtime;
import system.system_cinema.Model.Ticket;
import system.system_cinema.Repository.BookingRepository;
import system.system_cinema.Repository.CinemaHallRepository;
import system.system_cinema.Repository.MovieRepository;
import system.system_cinema.Repository.ShowTimeRepository;
import system.system_cinema.Service.ShowTimeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowTimeServiceImpl implements ShowTimeService {

    private final CinemaHallRepository cinemaHallRepository;
    private final MovieRepository movieRepository;
    private final ShowTimeRepository showtimeRepository;
    private final ShowtimeMapper showtimeMapper;
    private final BookingRepository bookingRepository;

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

    @Override
    public void createShowTime(ShowTimeRequestCreate requestCreate) {
        if (requestCreate.getMovieId() != null
                && requestCreate.getTimeSheet() != null) {
            Movie movie = movieRepository.findById(requestCreate.getMovieId())
                    .orElseThrow(() -> new RuntimeException("Movie not found"));
            List<Showtime> showTimes = new ArrayList<>();
            LocalDateTime today = LocalDateTime.now();
            for (DetailShowTime d : requestCreate.getTimeSheet()){
                showTimes.add(
                        Showtime
                                .builder()
                                .cinemaHall(cinemaHallRepository.findById(d.getRoomId())
                                        .orElseThrow(() -> new RuntimeException("Room not found")))
                                .movie(movie)
                                .dateCreate(today)
                                .startTime(d.getTimeStart())
                                .endTime(d.getTimeStart().plusMinutes(ConvertStringToInt(movie.getDuration())))
                                .build()
                );
            }
            showtimeRepository.saveAll(showTimes);
        } else {
            throw new RuntimeException("Data from request must not null");
        }
    }

    @Override
    public void updateShowTime(String showTimeId, String roomId) {
        Showtime showtime = showtimeRepository.findById(showTimeId).orElseThrow(() -> new RuntimeException("ShowTime not found"));
        showtime.setCinemaHall(cinemaHallRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found")));
        showtimeRepository.save(showtime);
    }

    @Override
    public void deleteShowTime(String showTimeId) {
        Showtime showtime = showtimeRepository.findById(showTimeId).orElseThrow(() -> new RuntimeException("Show time not found"));
        List<Ticket> ticket = bookingRepository.findByShowtime(showtime);
        if (ticket.isEmpty()) {
           showtimeRepository.delete(showtime);
        } else {
            throw new RuntimeException("Showtime has been booked, can not delete");
        }
    }

    @Override
    public List<?> getListShowTime(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return showtimeRepository.findByStartTimeBetweenOrderByMovie(startOfDay, endOfDay);
    }

    @Override
    public List<?> getAllShowTimes() {
        return showtimeRepository.findAll(Sort.by("startTime").descending());
    }

    //    Function additional
    private int ConvertStringToInt(String s){
        return Integer.parseInt(s.split(" ")[0]);
    }
}
