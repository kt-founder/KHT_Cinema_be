package system.system_cinema.Service.ServiceImplement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.ComboDetailRequest;
import system.system_cinema.DTO.Request.ComboRequest;
import system.system_cinema.DTO.Response.ComboResponse;
import system.system_cinema.Mapper.ComboMapper;
import system.system_cinema.Model.Combo;
import system.system_cinema.Model.Combo_Detail;
import system.system_cinema.Model.Snack;
import system.system_cinema.Repository.ComboRepository;
import system.system_cinema.Repository.SnackRepository;
import system.system_cinema.Service.ComboService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComboServiceImp implements ComboService {
    private final SnackRepository snackRepository;
    private final ComboRepository comboRepository;
    private final ComboMapper comboMapper;

    @Override
    public Map<String, Object> GetComboAndSnack() {
        List<Snack> snacks = snackRepository.findAllActiveSnacks();
        List<ComboResponse> combos = comboRepository.findAllActiveCombo().stream().map(comboMapper::toResponse).toList();
        Map<String, Object> result = new HashMap<>();
        result.put("snack", snacks);
        result.put("combo", combos);
        return result;
    }

    @Override
    public List<ComboResponse> getCombo() {
        return comboRepository.findAll().stream().map(comboMapper::toResponse).toList();
    }

    @Override
    public void CreateCombo(ComboRequest comboRequest) {
        Combo combo = Combo.builder()
                .name(comboRequest.getName())
                .price(comboRequest.getPrice())
                .active(true)
                .img(comboRequest.getImg())
                .build();
        List<Combo_Detail> combo_details = new ArrayList<>();
        for (ComboDetailRequest r : comboRequest.getSnacks()){
            Snack snack = snackRepository.findById(r.getId()).orElseThrow(() -> new RuntimeException("Snack not found"));
            Combo_Detail combo_Detail = Combo_Detail.builder()
                    .snack(snack)
                    .quantity(r.getQuantity())
                    .build();
            combo_details.add(combo_Detail);
        }
        combo.setComboDetails(combo_details);
        for (Combo_Detail combo_detail : combo_details){
            combo_detail.setCombo(combo);
        }
        comboRepository.save(combo);
    }

    @Override
    public void EditCombo(ComboRequest comboRequest) {
        Combo combo = Combo.builder()
                .id(comboRequest.getId())
                .name(comboRequest.getName())
                .price(comboRequest.getPrice())
                .img(comboRequest.getImg())
                .active(true)
                .build();
        List<Combo_Detail> combo_details = new ArrayList<>();
        for (ComboDetailRequest r : comboRequest.getSnacks()){
            Snack snack = snackRepository.findById(r.getId()).orElseThrow(() -> new RuntimeException("Snack not found"));
            Combo_Detail combo_Detail = Combo_Detail.builder()
                    .snack(snack)
                    .quantity(r.getQuantity())
                    .build();
            combo_details.add(combo_Detail);
        }
        combo.setComboDetails(combo_details);
        for (Combo_Detail combo_detail : combo_details){
            combo_detail.setCombo(combo);
        }
        comboRepository.save(combo);
    }

    @Override
    public void DeleteCombo(String comboId) {
        Combo combo = comboRepository.findById(comboId).orElseThrow(() -> new RuntimeException("Combo not found"));
        combo.setActive(!combo.isActive());
        comboRepository.save(combo);
    }
}
