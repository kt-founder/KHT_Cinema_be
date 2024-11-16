package system.system_cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String seatNumber;

    @ManyToOne
    @JoinColumn(name = "cinema_hall_id")
    private CinemaHall cinemaHall;


    @JsonIgnore
    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private List<SeatBooking> seatBookings;

    @ManyToOne
    @JoinColumn(name = "type_seat_id") // Thêm tên cột rõ ràng trong cơ sở dữ liệu
    private TypeSeat typeSeat; // Đặt tên đúng để ánh xạ chính xác
}
