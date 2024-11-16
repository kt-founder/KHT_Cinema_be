package system.system_cinema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import system.system_cinema.DTO.Response.SeatResponse;
import system.system_cinema.Model.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, String> {
    List<Seat> findByCinemaHallId(String cinemaHallId);

    @Query("SELECT new system.system_cinema.DTO.Response.SeatResponse(" +
            "s.id, s.seatNumber, ts.typeName, ts.price, " +
            "COALESCE(sb.status, 'available')) " +
            "FROM Seat s " +
            "LEFT JOIN s.typeSeat ts " +
            "LEFT JOIN SeatBooking sb ON s.id = sb.seat.id AND sb.ticket.showtime.id = :showtimeId " +
            "WHERE s.cinemaHall.id = (SELECT st.cinemaHall.id FROM Showtime st WHERE st.id = :showtimeId)")
    List<SeatResponse> findSeatsWithTypeAndStatusByShowTime(@Param("showtimeId") String showtimeId);
}
