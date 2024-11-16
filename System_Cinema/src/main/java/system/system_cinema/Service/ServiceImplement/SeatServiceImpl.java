package system.system_cinema.Service.ServiceImplement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.SeatRequest;
import system.system_cinema.DTO.Response.SeatResponse;
import system.system_cinema.Mapper.SeatMapper;
import system.system_cinema.Model.CinemaHall;
import system.system_cinema.Model.Seat;
import system.system_cinema.Model.TypeSeat;
import system.system_cinema.Repository.CinemaHallRepository;
import system.system_cinema.Repository.SeatRepository;
import system.system_cinema.Repository.TypeSeatRepository;
import system.system_cinema.Service.SeatService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final CinemaHallRepository cinemaHallRepository;
    private final TypeSeatRepository typeSeatRepository;
    private final SeatMapper seatMapper;

    @Override
    public List<SeatResponse> getSeatsByCinemaHall(String cinemaHallId) {
        return seatRepository.findByCinemaHallId(cinemaHallId).stream()
                .map(seatMapper::toSeatResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SeatResponse createSeat(SeatRequest request) {
        CinemaHall cinemaHall = cinemaHallRepository.findById(request.getCinemaHallId())
                .orElseThrow(() -> new RuntimeException("Cinema Hall not found"));

        TypeSeat typeSeat = typeSeatRepository.findById(request.getTypeSeatId())
                .orElseThrow(() -> new RuntimeException("TypeSeat not found"));

        Seat seat = seatMapper.toSeat(request);
        seat.setCinemaHall(cinemaHall);
        seat.setTypeSeat(typeSeat);

        Seat savedSeat = seatRepository.save(seat);
        return seatMapper.toSeatResponse(savedSeat);
    }

    @Override
    public SeatResponse updateSeat(String id, SeatRequest request) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        CinemaHall cinemaHall = cinemaHallRepository.findById(request.getCinemaHallId())
                .orElseThrow(() -> new RuntimeException("Cinema Hall not found"));

        TypeSeat typeSeat = typeSeatRepository.findById(request.getTypeSeatId())
                .orElseThrow(() -> new RuntimeException("TypeSeat not found"));

        seat.setSeatNumber(request.getSeatNumber());
        seat.setCinemaHall(cinemaHall);
        seat.setTypeSeat(typeSeat);

        Seat updatedSeat = seatRepository.save(seat);
        return seatMapper.toSeatResponse(updatedSeat);
    }

    @Override
    public void deleteSeat(String id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        seatRepository.delete(seat);
    }
}
