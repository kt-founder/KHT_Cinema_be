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
public class ComboRequest {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String name,id,img;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    int price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<ComboDetailRequest> snacks;
}