package com.example.bankapplication.domain.currency;

import com.example.bankapplication.domain.currency.rate.RateCDTO;
import java.util.List;
public class CurrencyResponseCDTO extends CurrencyResponseDTO{
        public List<RateCDTO> rates;
        public void AddInterval(List<CurrencyResponseCDTO> dto){
                for(var elem : dto){
                        this.rates.addAll(elem.rates);
                }
        }
}
