package system.system_cinema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.system_cinema.Model.CinemaHall;

@Repository
public interface CinemaHallRepository extends JpaRepository<CinemaHall, String> {
}
