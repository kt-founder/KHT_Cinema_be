package system.system_cinema.Service;

import system.system_cinema.DTO.Request.TicketRequest;
import system.system_cinema.DTO.Response.*;

import java.util.List;

public interface TicketService {
    List<StatusTicket> getStatusTickets();
    List<TicketResponse> getTicketsByUser(String userId);
    List<StatisticPriceMonthResponse> statisticPriceMonth();
    List<StatisticUserTicket> statisticUserTicket();
    List<StatisticMovieRevenue> statisticMovieRevenue();
}
