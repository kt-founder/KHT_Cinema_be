package system.system_cinema.Mapper;

import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Request.SeatRequest;
import system.system_cinema.DTO.Response.SeatResponse;
import system.system_cinema.Model.Seat;

@Component
public class SeatMapper {
    public SeatResponse toSeatResponse(Seat seat) {
        SeatResponse response = new SeatResponse();
        response.setId(seat.getId());
        response.setSeatNumber(seat.getSeatNumber());
        response.setCinemaHallId(seat.getCinemaHall().getId());
        response.setTypeSeatId(seat.getTypeSeat().getId());
        return response;
    }

    public Seat toSeat(SeatRequest request) {
        return Seat.builder()
                .seatNumber(request.getSeatNumber())
                .build();
    }
}
