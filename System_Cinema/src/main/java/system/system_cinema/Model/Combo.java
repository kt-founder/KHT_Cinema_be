package system.system_cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Combo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    String name, img;
    int price;
    boolean active;
    @OneToMany(mappedBy = "combo", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Combo_Detail> comboDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "combo")
    List<FoodBeverageOrder> foodBeverageOrders;
}
