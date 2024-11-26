package com.academia.iglesia.service;

import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.model.Pago;
import com.academia.iglesia.repository.IPagoRepository;
import com.academia.iglesia.repository.MiembrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService implements  IPagoService {
    @Autowired
    private IPagoRepository pagoRepository;
    @Autowired
    private MiembrosRepository miembrosRepository;

    @Override
    public List<Pago> get() {
        List<Pago> pagos= pagoRepository.findAll();
        return pagos;
    }

    @Override
    public Pago save(Pago pago) {
        Pago pagoSaved= new Pago();
        pagoSaved.setFecha_pago(pago.getFecha_pago());
        pagoSaved.setMetodoPago(pago.getMetodoPago());
        pagoSaved.setReferencia(pago.getReferencia());
        pagoSaved.setMonto(pago.getMonto());
        pagoSaved.setObservacion(pago.getObservacion());
        Miembro miembro= miembrosRepository.findByCedula(pago.getMiembro().getCedula());
        pagoSaved.setMiembro(miembro);
        pagoRepository.save(pagoSaved);
        return pagoSaved;
    }

    @Override
    public void delete(String idPago) {
  pagoRepository.deleteById(idPago);
    }

    @Override
    public Pago find(String idPago) {

        Pago pago= pagoRepository.findById(idPago).orElseThrow(null);
        return pago;
    }

    @Override
    public Pago edit(String idPago, Pago pago) {
        Pago pagoFind= this.find(idPago);
        pagoFind.setFecha_pago(pago.getFecha_pago());
        pagoFind.setMetodoPago(pago.getMetodoPago());
        pagoFind.setMiembro(pago.getMiembro());
        pagoFind.setReferencia(pago.getReferencia());
        pagoFind.setObservacion(pago.getObservacion());
        pagoRepository.save(pagoFind);
        return pagoFind;
    }
}
