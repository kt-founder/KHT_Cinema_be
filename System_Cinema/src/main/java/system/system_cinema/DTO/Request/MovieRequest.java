package system.system_cinema.DTO.Request;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieRequest {
    String title;
    String genre;
    String description;
    String director;
    LocalDate releaseDate;
    String image;
    boolean isActive;
}
