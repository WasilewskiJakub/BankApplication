package com.example.bankapplication.services.configuration;

public enum Currency {
    USD, CHF, EUR, GBP;
    public String toString(){
        return super.toString().toLowerCase();
    }
}
