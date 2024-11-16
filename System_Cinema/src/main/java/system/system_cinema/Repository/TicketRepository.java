package system.system_cinema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.system_cinema.Model.Ticket;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {
    List<Ticket> findByShowtimeId(String showtimeId);
    List<Ticket> findByUserId(String userId);
}
