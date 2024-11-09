package system.system_cinema.DTO.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowTimeAndRoomResponse {
    String id;
    LocalDateTime dateCreate,startTime, endTime;
    String roomId, nameRoom;
}
