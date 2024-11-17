package system.system_cinema.DTO.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LockSeatsRequest {
    List<String> seatIds;
    String showtimeId;
    String userId;
}
