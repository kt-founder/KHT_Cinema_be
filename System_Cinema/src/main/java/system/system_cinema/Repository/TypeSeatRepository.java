package system.system_cinema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.system_cinema.Model.TypeSeat;

public interface TypeSeatRepository extends JpaRepository<TypeSeat, String> {
}
