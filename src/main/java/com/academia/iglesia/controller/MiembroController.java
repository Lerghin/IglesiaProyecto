package com.academia.iglesia.controller;

import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.service.IMiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000/")
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
    public ResponseEntity<Object> delete(@PathVariable String idMiembro ) throws RuntimeException  {
        miembroService.delete(idMiembro);
        return ResponseEntity.noContent().build();

    }


    @GetMapping("/get/{idMiembro}")
    public Miembro find(@PathVariable String idMiembro) throws  RuntimeException{
        Miembro miembro= miembroService.findMiembro(idMiembro);
        if (miembro == null) {
            throw new RuntimeException("Member with ID " + idMiembro + " not found");
        }
        return miembro;
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


}
