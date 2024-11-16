package system.system_cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    String typeName; // Tên loại ghế (thêm vào theo yêu cầu trước đó)
    int price;

    @JsonIgnore
    @OneToMany(mappedBy = "typeSeat", cascade = CascadeType.ALL)
    private List<Seat> seats; // Đặt tên chính xác và đồng nhất
}
