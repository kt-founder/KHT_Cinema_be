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

    int price; // Tổng giá vé (dựa trên ghế đã đặt)
    boolean isPaid; // Trạng thái thanh toán
    LocalDateTime dateBooking; // Thời gian đặt vé

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user; // Người đặt vé

    @ManyToOne
    @JoinColumn(name = "showtime_id")
    Showtime showtime; // Xuất chiếu

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    List<SeatBooking> seatBookings; // Danh sách ghế trong vé
}
