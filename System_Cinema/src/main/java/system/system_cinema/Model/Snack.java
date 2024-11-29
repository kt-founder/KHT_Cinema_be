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
public class Snack {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    String name, img;
    int price;
    boolean active;
    @JsonIgnore
    @OneToMany(mappedBy = "snack")
    List<Combo_Detail> combo_details;

    @JsonIgnore
    @OneToMany(mappedBy = "snack")
    List<FoodBeverageOrder> food_beverage_orders;
}
