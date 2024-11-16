package system.system_cinema.Mapper;

import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Request.TicketRequest;
import system.system_cinema.DTO.Response.TicketResponse;
import system.system_cinema.Model.Ticket;

@Component
public class TicketMapper {
    public TicketResponse toTicketResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.setId(ticket.getId());
        response.setPrice(ticket.getPrice());
        response.setPaid(ticket.isPaid());
        response.setDateBooking(ticket.getDateBooking());
        response.setUserId(ticket.getUser().getId());
        response.setShowtimeId(ticket.getShowtime().getId());
        return response;
    }

    public Ticket toTicket(TicketRequest request) {
        return Ticket.builder()
                .price(request.getPrice())
                .isPaid(request.isPaid())
                .build();
    }
}
