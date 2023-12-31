package com.example.bankapplication.domain;

import java.util.ArrayList;
import java.util.List;

public class CurrencyResponseDTO {
    public String table;
    public String currency;
    public String code;
    public List<RateDTO> rates;
}
