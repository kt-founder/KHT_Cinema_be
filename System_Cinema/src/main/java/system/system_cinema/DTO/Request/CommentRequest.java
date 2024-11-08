package system.system_cinema.DTO.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentRequest {
    String movieId;
    String content;
    String parentCommentId;
    int rate;  // Thêm trường đánh giá
}
