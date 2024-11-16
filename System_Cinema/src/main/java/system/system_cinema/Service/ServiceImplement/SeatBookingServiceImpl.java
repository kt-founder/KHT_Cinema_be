package system.system_cinema.Service.ServiceImplement;

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

    private final SeatBookingRepository seatBookingRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final SeatBookingMapper seatBookingMapper;

    @Override
    public List<SeatBookingResponse> getSeatBookingsByTicket(String ticketId) {
        return seatBookingRepository.findByTicketId(ticketId).stream()
                .map(seatBookingMapper::toSeatBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SeatBookingResponse createSeatBooking(SeatBookingRequest request) {
        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        Ticket ticket = ticketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        SeatBooking seatBooking = seatBookingMapper.toSeatBooking(request);
        seatBooking.setSeat(seat);
        seatBooking.setTicket(ticket);

        SeatBooking savedSeatBooking = seatBookingRepository.save(seatBooking);
        return seatBookingMapper.toSeatBookingResponse(savedSeatBooking);
    }
}
