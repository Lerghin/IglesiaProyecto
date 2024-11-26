package com.academia.iglesia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(value = "pago")
@Data
public class Pago {

    @Id
    private String idPago;
    @DBRef
    private Miembro miembro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha_pago;
    private String metodoPago;
    private String referencia;
    private String observacion;
    private Double monto;

}
