package com.academia.iglesia.controller;

import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Pago;
import com.academia.iglesia.service.ICursoService;
import com.academia.iglesia.service.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    private IPagoService pagoService;


    @GetMapping("/get")
    public List<Pago> get() throws  RuntimeException{
        List<Pago> pagos= pagoService.get();
        if(pagos.isEmpty()){
            throw new RuntimeException("No members found");
        }

        return pagos;
    }


    @GetMapping("/get/ingresos")
    public Map<String, Double> getIngresos() {
        List<Pago> pagos = pagoService.get();
        if (pagos.isEmpty()) {
            throw new RuntimeException("No payments found");
        }

        Map<String, Double> ingresosPorMes = new HashMap<>();

        for (Pago pago : pagos) {
            // Extraer el mes y el año de la fecha de pago (aquí usamos "MMMM yyyy" para obtener el mes y el año)
            String mes = pago.getFecha_pago().getMonth().toString() + " " + pago.getFecha_pago().getYear();

            // Sumar el monto del pago al total del mes
            double monto = pago.getMonto();  // Suponiendo que 'getMonto()' devuelve el valor del pago
            ingresosPorMes.put(mes, ingresosPorMes.getOrDefault(mes, 0.0) + monto);
        }

        return ingresosPorMes;
    }


    @PostMapping("/create")
    public String create(@RequestBody Pago pago){

       Pago pago1= pagoService.save(pago);

        return  "Creado Satisfactoriamente";
    }

    @DeleteMapping("/delete/{idPago}")
    public ResponseEntity<Object> delete(@PathVariable String idPago ) throws RuntimeException  {
       pagoService.delete(idPago);
        return ResponseEntity.noContent().build();

    }


    @GetMapping("/get/{idPago}")
    public Pago find(@PathVariable String idPago) throws  RuntimeException{
       Pago pago= pagoService.find(idPago);
        if (pago == null) {
            throw new RuntimeException("Member with ID " + idPago + " not found");
        }
        return pago;
    }

    @PutMapping("/{idPago}")
    public Pago edit(@PathVariable String idPago, @RequestBody Pago pago) throws  RuntimeException{
       Pago existingPago = pagoService.find(idPago); // Check for existing member before edit
        if (existingPago == null) {
            throw new RuntimeException("Member with ID " + idPago + " not found");
        }
        Pago pagoEdited = pagoService.edit(idPago, pago);
        return pagoEdited;

    }
}
