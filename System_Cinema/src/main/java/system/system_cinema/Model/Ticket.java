package system.system_cinema.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket {
    @Id
    private String id;

    int price;
    boolean isPaid;
    LocalDateTime dateBooking;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "showtime_id")
    Showtime showtime;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    List<SeatBooking> seatBookings;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    List<FoodBeverageOrder> foodBeverageOrders;
}
