package com.example.bankapplication.domain.gold;

import com.example.bankapplication.domain.IResponseDTO;
import javafx.util.Pair;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GoldRateResponseDTO implements IResponseDTO {
    public List<GoldPriceDTO> cenaZlota;

    @Override
    public List<Pair<String, Double>> GetData() {
        List<Pair<String, Double>> list = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for(GoldPriceDTO price : cenaZlota){
            list.add(new Pair<>(price.data.format(formatter),price.cena));
        }
        return list;
    }
    public GoldRateResponseDTO(){
        this.cenaZlota = new ArrayList<>();
    }
    public GoldRateResponseDTO(List<List<GoldPriceDTO>> results){
        this.cenaZlota = new ArrayList<>();
        for(var list : results){
            this.cenaZlota.addAll(list);
        }
    }
}
