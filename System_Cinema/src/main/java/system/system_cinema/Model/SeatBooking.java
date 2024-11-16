package system.system_cinema.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat; // Ghế được đặt

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket; // Vé liên quan
}
