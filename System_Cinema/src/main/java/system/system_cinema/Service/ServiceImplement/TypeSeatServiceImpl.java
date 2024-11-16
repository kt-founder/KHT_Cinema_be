package system.system_cinema.Service.ServiceImplement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.TypeSeatRequest;
import system.system_cinema.DTO.Response.TypeSeatResponse;
import system.system_cinema.Mapper.TypeSeatMapper;
import system.system_cinema.Model.TypeSeat;
import system.system_cinema.Repository.TypeSeatRepository;
import system.system_cinema.Service.TypeSeatService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeSeatServiceImpl implements TypeSeatService {

    private final TypeSeatRepository typeSeatRepository;
    private final TypeSeatMapper typeSeatMapper;

    @Override
    public List<TypeSeatResponse> getAllTypeSeats() {
        return typeSeatRepository.findAll().stream()
                .map(typeSeatMapper::toTypeSeatResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TypeSeatResponse createTypeSeat(TypeSeatRequest request) {
        TypeSeat typeSeat = typeSeatMapper.toTypeSeat(request);
        TypeSeat savedTypeSeat = typeSeatRepository.save(typeSeat);
        return typeSeatMapper.toTypeSeatResponse(savedTypeSeat);
    }

    @Override
    public TypeSeatResponse updateTypeSeat(String id, TypeSeatRequest request) {
        TypeSeat typeSeat = typeSeatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypeSeat not found"));

        typeSeat.setTypeName(request.getTypeName());
        typeSeat.setPrice(request.getPrice());

        TypeSeat updatedTypeSeat = typeSeatRepository.save(typeSeat);
        return typeSeatMapper.toTypeSeatResponse(updatedTypeSeat);
    }

    @Override
    public void deleteTypeSeat(String id) {
        TypeSeat typeSeat = typeSeatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypeSeat not found"));
        typeSeatRepository.delete(typeSeat);
    }
}
