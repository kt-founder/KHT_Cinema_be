package system.system_cinema.Mapper;

import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Request.TicketRequest;
import system.system_cinema.DTO.Response.TicketResponse;
import system.system_cinema.Model.SeatBooking;
import system.system_cinema.Model.Ticket;

import java.util.ArrayList;
import java.util.List;

@Component
public class TicketMapper {
    public TicketResponse toTicketResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.setId(ticket.getId());
        response.setPrice(ticket.getPrice());
        response.setDateBooking(ticket.getDateBooking());
        response.setMovie(ticket.getShowtime().getMovie().getTitle());
        List<String> info = new ArrayList<>();
        info.add(ticket.getShowtime().getStartTime().toString());
        info.add(ticket.getShowtime().getEndTime().toString());
        info.add(ticket.getShowtime().getCinemaHall().getName());
        response.setInfoShowTime(info);

        List<String> seat = new ArrayList<>();
        for (SeatBooking sb : ticket.getSeatBookings()) {
            seat.add(sb.getSeat().getSeatNumber());
        }
        response.setSeatIds(seat);
        return response;
    }

    public Ticket toTicket(TicketRequest request) {
        return Ticket.builder()
                .price(request.getPrice())
                .isPaid(request.isPaid())
                .build();
    }
}
