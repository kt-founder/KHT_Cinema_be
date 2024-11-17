package system.system_cinema.Service.ServiceImplement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.SeatBookingRequest;
import system.system_cinema.DTO.Response.SeatBookingResponse;
import system.system_cinema.Mapper.SeatBookingMapper;
import system.system_cinema.Model.Seat;
import system.system_cinema.Model.SeatBooking;
import system.system_cinema.Model.Ticket;
import system.system_cinema.Repository.SeatBookingRepository;
import system.system_cinema.Repository.SeatRepository;
import system.system_cinema.Repository.TicketRepository;
import system.system_cinema.Service.SeatBookingService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatBookingServiceImpl implements SeatBookingService {
    SeatBookingRepository seatBookingRepository;
    @Override
    @Transactional
    public boolean lockSeats(List<String> seatIds, String showtimeId, String userId) {
        List<SeatBooking> existingSeats = seatBookingRepository.findBySeatIdInAndShowTimeId(seatIds, showtimeId);

        for (SeatBooking seat : existingSeats) {
            if ("sold".equals(seat.getStatus()) || "held".equals(seat.getStatus())) {
                return false;
            }
        }

        for (String seatId : seatIds) {
            SeatBooking seatBooking = SeatBooking.builder().build();
            seatBooking.setStatus("held");
            seatBookingRepository.save(seatBooking);
        }

        return true;
    }
}
