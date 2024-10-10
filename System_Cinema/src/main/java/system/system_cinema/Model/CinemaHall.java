package system.system_cinema.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int totalSeats;

    @OneToMany(mappedBy = "cinemaHall", cascade = CascadeType.ALL)
    private List<Seat> seats;

    // Constructors, Getters, and Setters
    public CinemaHall() {}

    public CinemaHall(String name, int totalSeats) {
        this.name = name;
        this.totalSeats = totalSeats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
