package com.example.bankapplication.domain;

import javafx.util.Pair;

import java.util.List;

public interface IResponseDTO {
    public List<Pair<String, Double>> GetData();
}
