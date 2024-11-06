package system.system_cinema.Service.ServiceImplement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.ShowtimeRequest;
import system.system_cinema.DTO.Response.CinemaHallResponse;
import system.system_cinema.Mapper.CinemaHallMapper;
import system.system_cinema.Model.CinemaHall;
import system.system_cinema.Model.Movie;
import system.system_cinema.Model.Showtime;
import system.system_cinema.Repository.CinemaHallRepository;
import system.system_cinema.Repository.MovieRepository;
import system.system_cinema.Repository.ShowTimeRepository;
import system.system_cinema.Service.CinemaHallService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CinemaHallServiceImpl implements CinemaHallService {

    private final CinemaHallRepository cinemaHallRepository;
    private final ShowTimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    private final CinemaHallMapper cinemaHallMapper;

    @Override
    public CinemaHallResponse getCinemaHallById(String id) {
        CinemaHall cinemaHall = cinemaHallRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cinema Hall not found"));
        return cinemaHallMapper.toCinemaHallResponse(cinemaHall);
    }

    @Override
    public List<CinemaHallResponse> getAllCinemaHalls() {
        return cinemaHallRepository.findAll().stream()
                .map(cinemaHallMapper::toCinemaHallResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CinemaHallResponse changeCinemaHallStatus(String id, boolean isActive) {
        CinemaHall cinemaHall = cinemaHallRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cinema Hall not found"));
        cinemaHall.setActive(isActive);
        CinemaHall updatedCinemaHall = cinemaHallRepository.save(cinemaHall);
        return cinemaHallMapper.toCinemaHallResponse(updatedCinemaHall);
    }

    @Override
    public CinemaHallResponse addShowtime(String cinemaHallId, ShowtimeRequest showtimeRequest) {
        CinemaHall cinemaHall = cinemaHallRepository.findById(cinemaHallId)
                .orElseThrow(() -> new RuntimeException("Cinema Hall not found"));

        Movie movie = movieRepository.findById(showtimeRequest.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Showtime showtime = Showtime.builder()
                .cinemaHall(cinemaHall)
                .movie(movie)
                .startTime(showtimeRequest.getStartTime())
                .endTime(showtimeRequest.getEndTime())
                .build();

        showtimeRepository.save(showtime);

        return cinemaHallMapper.toCinemaHallResponse(cinemaHall);
    }

    @Override
    public List<CinemaHallResponse> checkAvailability(LocalDateTime time) {
        List<Showtime> ongoingShowtimes = showtimeRepository.findByEndTimeAfter(time);

        // Lấy danh sách các phòng đang có suất chiếu tại thời điểm được cung cấp
        List<CinemaHall> occupiedRooms = ongoingShowtimes.stream()
                .map(Showtime::getCinemaHall)
                .distinct()
                .toList();

        // Tìm các phòng không có suất chiếu nào đang diễn ra hoặc sắp diễn ra sau thời điểm đó
        return cinemaHallRepository.findAll().stream()
                .filter(room -> !occupiedRooms.contains(room))
                .toList().stream().map(cinemaHallMapper::toCinemaHallResponse).toList();
    }
}
