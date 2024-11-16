package system.system_cinema.Service.ServiceImplement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.TicketRequest;
import system.system_cinema.DTO.Response.TicketResponse;
import system.system_cinema.Mapper.TicketMapper;
import system.system_cinema.Model.Showtime;
import system.system_cinema.Model.Ticket;
import system.system_cinema.Model.User;
import system.system_cinema.Repository.ShowTimeRepository;
import system.system_cinema.Repository.TicketRepository;
import system.system_cinema.Repository.UserRepository;
import system.system_cinema.Service.TicketService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ShowTimeRepository showtimeRepository;
    private final TicketMapper ticketMapper;

    @Override
    public List<TicketResponse> getTicketsByUser(String userId) {
        return ticketRepository.findByUserId(userId).stream()
                .map(ticketMapper::toTicketResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TicketResponse createTicket(TicketRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Showtime showtime = showtimeRepository.findById(request.getShowtimeId())
                .orElseThrow(() -> new RuntimeException("Showtime not found"));

        Ticket ticket = ticketMapper.toTicket(request);
        ticket.setUser(user);
        ticket.setShowtime(showtime);

        Ticket savedTicket = ticketRepository.save(ticket);
        return ticketMapper.toTicketResponse(savedTicket);
    }
}
