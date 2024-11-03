package system.system_cinema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.system_cinema.Model.Showtime;

import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<Showtime, String> {

    List<Showtime> findByStartTimeContainingOrderByMovie(String value);
}
