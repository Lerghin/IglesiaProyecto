package com.academia.iglesia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
@Document(value = "professor")
@Data
public class Professor {

    @Id
    private String idProfessor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha_nacimiento;
    private String name;
    private String lastName;
    private String email;
    private String address;
    private String cedula;
    private List<String> cursos;
    private String phone;


    @JsonProperty("edad")
    public int getEdad(){
        LocalDate today = LocalDate.now();
        return Period.between(fecha_nacimiento, today).getYears();

    }

}
