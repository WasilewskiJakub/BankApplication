package com.example.bankapplication.domain.currency;

import com.example.bankapplication.domain.IResponseDTO;
import com.example.bankapplication.domain.currency.rate.RateABDTO;
import com.example.bankapplication.domain.gold.GoldPriceDTO;
import javafx.util.Pair;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CurrencyResponseABDTO extends CurrencyResponseDTO implements IResponseDTO {
    public List<RateABDTO> rates;

    @Override
    public List<Pair<String, Double>> GetData() {
        List<Pair<String, Double>> list = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for(RateABDTO price : rates){
            list.add(new Pair<>(price.effectiveDate.format(formatter),price.mid));
        }
        return list;
    }
}
