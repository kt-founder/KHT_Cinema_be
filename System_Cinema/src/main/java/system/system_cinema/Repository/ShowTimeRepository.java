package system.system_cinema.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import system.system_cinema.Model.Showtime;

public interface ShowTimeRepository extends JpaRepository<Showtime, String> {
}
