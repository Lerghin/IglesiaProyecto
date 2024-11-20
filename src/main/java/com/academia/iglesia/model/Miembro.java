package com.academia.iglesia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Document(value = "miembrosIglesia")
@Data
public class Miembro {

    @Id
    private String idMiembro;
    @Indexed(unique = true)
    private String cedula;
    private String nombre;
    private LocalDate fecha_nacimiento;
    private String apellido;
    private String direccion;
    private String ocupacion;
    private Sexo sexo;
    private String telefono;
    private String email;
    private StatusMiembro status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha_ingreso;

    private Map<String, Integer> cursosRealizados = new HashMap<>();


    @JsonProperty("tiempo")
    public int getTiempoMembresia() {
        LocalDate today = LocalDate.now();
        return Period.between(fecha_ingreso, today).getYears();

    }
    @JsonProperty("edad")
    public int getEdad(){
        LocalDate today = LocalDate.now();
        return Period.between(fecha_nacimiento, today).getYears();

    }


    // Método para agregar un curso o incrementar el número de veces que se ha realizado
    public void agregarCursoRealizado(String cursoNombre) {
        // Si el curso ya está en el mapa, incrementa el contador en 1
        cursosRealizados.put(cursoNombre, cursosRealizados.getOrDefault(cursoNombre, 0) + 1);
    }



}
