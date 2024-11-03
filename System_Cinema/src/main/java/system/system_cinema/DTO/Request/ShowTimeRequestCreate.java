package system.system_cinema.DTO.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowTimeRequestCreate {
    String movieId, roomId;
    LocalDateTime dateCreate;
    List<LocalDateTime> timeSheet;
}
