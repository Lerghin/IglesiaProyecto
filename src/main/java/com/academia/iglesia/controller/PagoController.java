package com.academia.iglesia.controller;

import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Pago;
import com.academia.iglesia.service.ICursoService;
import com.academia.iglesia.service.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @PostMapping("/create")
    public Pago create(@RequestBody Pago pago){

       Pago pago1= pagoService.save(pago);

        return  pago1;
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
