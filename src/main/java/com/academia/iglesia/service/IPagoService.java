package com.academia.iglesia.service;

import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Pago;

import java.util.List;

public interface IPagoService {
    public List<Pago> get();
    public Pago save(Pago pago);
    public void delete(String idPago);
    public Pago find(String  idPago);
    public Pago edit(String idPago, Pago pago);
}
