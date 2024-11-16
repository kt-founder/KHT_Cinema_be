package system.system_cinema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.system_cinema.Model.Seat;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, String> {
    List<Seat> findByCinemaHallId(String cinemaHallId);
}
