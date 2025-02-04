package com.academia.iglesia.controller;

import com.academia.iglesia.dto.ModuloNotaDTO;
import com.academia.iglesia.dto.PercentMiembrosDTO;
import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.model.Modulo;
import com.academia.iglesia.service.IMiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "https://dashboard-academy-church.vercel.app")
@RestController
@RequestMapping("/miembro")
public class MiembroController {
    @Autowired
    private IMiembroService miembroService;

    @GetMapping("/get")
    public List<Miembro> getSales() throws  RuntimeException{
        List<Miembro> miembros=miembroService.getMiembros();
        if(miembros.isEmpty()){
            throw new RuntimeException("No members found");
        }

        return miembros;
    }
    @PostMapping("/create")
    public String createMiembro(@RequestBody Miembro miembro){
        miembroService.save(miembro);
        return "Creado correctamente";
    }

    @DeleteMapping("/delete/{idMiembro}")
    public String delete(@PathVariable String idMiembro ) throws RuntimeException  {
        miembroService.delete(idMiembro);
        return "Borrado Correctamente";

    }


    @GetMapping("/get/{idMiembro}")
    public Miembro find(@PathVariable String idMiembro) throws  RuntimeException{
        Miembro miembro= miembroService.findMiembro(idMiembro);
        if (miembro == null) {
            throw new RuntimeException("Member with ID " + idMiembro + " not found");
        }
        return miembro;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get/number")
    public Integer miembrosNumber() throws  RuntimeException{
        int count= miembroService.countMember();
        return count;
    }
    @GetMapping("/get/percent")
    public PercentMiembrosDTO percentMiembros() throws  RuntimeException{
       PercentMiembrosDTO percentMiembrosDTO= miembroService.percent();
        return percentMiembrosDTO;
    }



    @PutMapping("/{idMiembro}")
    public Miembro editMiembro(@PathVariable String idMiembro, @RequestBody Miembro miembro) throws  RuntimeException{
        Miembro existingMiembro = miembroService.findMiembro(idMiembro); // Check for existing member before edit
        if (existingMiembro == null) {
            throw new RuntimeException("Member with ID " + idMiembro + " not found");
        }
        Miembro miembroEdited = miembroService.editMiembro(idMiembro, miembro);
        return miembroEdited;

    }
    @GetMapping("/distribucion-edad")
    public Map<String, Integer> obtenerDistribucionEdad() {
        return miembroService.obtenerDistribucionEdad();
    }


}
