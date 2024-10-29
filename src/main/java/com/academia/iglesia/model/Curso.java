package com.academia.iglesia.model;

import com.academia.iglesia.dto.MiembroDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(value = "curso")
@Data
public class Curso {
   @Id
   private String idCurso;
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
   private LocalDate fecha_inicio;
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
   private LocalDate fecha_fin;
   private String nombreCurso;
   @DBRef
   private  List<Miembro> participantes;
   @DBRef
   private  List<Professor> professor;
   private String descripcion;
   private List<Modulo> moduloList;





}
