package system.system_cinema.DTO.Request;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<DetailsFvB> snack, combo;
}
