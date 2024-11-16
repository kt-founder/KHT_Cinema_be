package system.system_cinema.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketResponse {
    private String id;
    private int price;
    private boolean isPaid;
    private LocalDateTime dateBooking;
    private String userId;
    private String showtimeId;
    private List<String> seatIds; // Danh sách các ghế liên quan
}
