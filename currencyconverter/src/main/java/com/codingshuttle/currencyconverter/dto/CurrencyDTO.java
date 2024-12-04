package com.codingshuttle.currencyconverter.dto;


import lombok.*;

import java.util.Map;


@Data
public class CurrencyDTO {


    //data is getting returned in api response
    private Map<String,Double> data;
    public Map<String, Double> getData() {
        return data;
    }

    public void setData(Map<String, Double> data) {
        this.data = data;
    }



}
