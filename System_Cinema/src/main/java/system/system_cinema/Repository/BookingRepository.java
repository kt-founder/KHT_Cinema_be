package system.system_cinema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.system_cinema.Model.Showtime;
import system.system_cinema.Model.Ticket;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Ticket, String>
{
    List<Ticket> findByShowtime(Showtime showtime);
}
