package com.example.bankapplication.domain.currency;

import com.example.bankapplication.domain.currency.rate.RateABDTO;
import java.util.List;

public class CurrencyResponseABDTO extends CurrencyResponseDTO{
    public List<RateABDTO> rates;
}
