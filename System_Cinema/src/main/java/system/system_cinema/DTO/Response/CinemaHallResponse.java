package system.system_cinema.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CinemaHallResponse {
    String id;
    String name;
    boolean isActive;
    List<ShowtimeResponse> showtimes;  // Danh sách giờ chiếu
}
