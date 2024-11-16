package system.system_cinema.DTO.Request;

import lombok.Data;

@Data
public class SeatRequest {
    private String seatNumber;       // Số ghế
    private String cinemaHallId;     // ID phòng chiếu
    private String typeSeatId;       // ID loại ghế
}
