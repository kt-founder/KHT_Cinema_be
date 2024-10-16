package system.system_cinema.Repository;

import system.system_cinema.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    List<Movie> findByTitleContaining(String title);  // Tìm kiếm phim theo tiêu đề
}
