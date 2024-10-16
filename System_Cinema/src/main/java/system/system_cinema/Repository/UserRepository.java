package system.system_cinema.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import system.system_cinema.DTO.Response.UserResponse;
import system.system_cinema.Model.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query("select new system.system_cinema.DTO.Response.UserResponse(u.id, u.name, u.email, u.phone, u.username)" +
            "from User u where u.username = :value")
    UserResponse findByName(@Param("value") String name);
    @Query("select new system.system_cinema.DTO.Response.UserResponse(u.id, u.name, u.email, u.phone,u.username)" +
            "from User u join u.roles r where r.name = 'USER'")
    List<UserResponse> findUsers();
    @Query("select u from User u join u.roles r where r.name = 'USER' and u.username = :value")
    Optional<User> findUser(String value);
}
