package system.system_cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Combo_Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    int quantity;

    @ManyToOne
    Snack snack;

    @JsonIgnore
    @ManyToOne
    Combo combo;
}
