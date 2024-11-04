package system.system_cinema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import system.system_cinema.Model.Showtime;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<Showtime, String> {
    @Query("select st from Showtime st where st.startTime = :value")
    List<Showtime> findByStartTimeContainingOrderByMovie(LocalDate value);
}
