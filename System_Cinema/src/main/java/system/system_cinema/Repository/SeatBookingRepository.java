package system.system_cinema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.system_cinema.Model.SeatBooking;

import java.util.List;

public interface SeatBookingRepository extends JpaRepository<SeatBooking, String> {
    List<SeatBooking> findByTicketId(String ticketId);
}
