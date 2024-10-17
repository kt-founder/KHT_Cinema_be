package system.system_cinema.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowtimeResponse {
    String id;
    String movieTitle;        // Tên phim
    LocalDateTime startTime;  // Giờ bắt đầu chiếu
    LocalDateTime endTime;    // Giờ kết thúc chiếu
}
