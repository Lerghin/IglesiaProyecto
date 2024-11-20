package com.academia.iglesia.dto;

import lombok.Data;

@Data
public class PercentMiembrosDTO {
    private int total;
    private int countMen;
    private int countWomen;
    private String fillWomen;
    private String fillMen;
    private String fillTot;
    private  double porcentWomen;
    private double porcenMen;


}
