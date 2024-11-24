package system.system_cinema.Service.ServiceImplement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.TicketRequest;
import system.system_cinema.DTO.Response.*;
import system.system_cinema.Mapper.TicketMapper;
import system.system_cinema.Model.Showtime;
import system.system_cinema.Model.Ticket;
import system.system_cinema.Model.User;
import system.system_cinema.Repository.*;
import system.system_cinema.Service.TicketService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ShowTimeRepository showtimeRepository;
    private final TicketMapper ticketMapper;
    private final BookingRepository bookingRepository;
    private final MovieRepository movieRepository;

    @Override
    public List<StatusTicket> getStatusTickets() {
        return ticketRepository.findAll().stream().map(ticketMapper::toStatusTicketResponse).toList();
    }

    @Override
    public List<TicketResponse> getTicketsByUser(String userId) {
        return ticketRepository.findByUserId(userId).stream()
                .map(ticketMapper::toTicketResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<StatisticPriceMonthResponse> statisticPriceMonth() {
        return bookingRepository.getMonthlyStatistics().stream().map(
                record -> new StatisticPriceMonthResponse(
                        (String) record[0],
                        ((BigDecimal) record[1]).longValue(),
                        ((Long) record[2])
                )
        ).collect(Collectors.toList());
    }

    @Override
    public List<StatisticUserTicket> statisticUserTicket() {
        return userRepository.statisticTicketUser().stream().map(
                record -> new StatisticUserTicket(
                        (String) record[0],
                        (Long) record[1]
                )
        ).collect(Collectors.toList());
    }

    @Override
    public List<StatisticMovieRevenue> statisticMovieRevenue() {
        return movieRepository.StatisticMovieRevenue().stream().map(
                record -> new StatisticMovieRevenue(
                        (String) record[0],
                        ((BigDecimal) record[1]).longValue()
                )
        ).collect(Collectors.toList());
    }

}
