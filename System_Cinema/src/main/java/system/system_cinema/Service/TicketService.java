package system.system_cinema.Service;

import system.system_cinema.DTO.Request.TicketRequest;
import system.system_cinema.DTO.Response.TicketResponse;

import java.util.List;

public interface TicketService {

    List<TicketResponse> getTicketsByUser(String userId);

    TicketResponse createTicket(TicketRequest request);
}
