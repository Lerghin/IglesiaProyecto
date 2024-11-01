package com.academia.iglesia.controller;

import com.academia.iglesia.dto.ModuloCursoDTO;
import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Modulo;
import com.academia.iglesia.service.ICursoService;
import com.academia.iglesia.service.IModuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/modulo")
public class ModuloController {
    @Autowired
    private IModuloService moduloService;

    @GetMapping("/get")
    public List<Modulo> get() throws  RuntimeException{
        List<Modulo> moduloList= moduloService.get();
        if(moduloList.isEmpty()){
            throw new RuntimeException("No members found");
        }

        return moduloList;
    }
    @GetMapping("/getmodulos")
    public List<ModuloCursoDTO> getMDTO() throws  RuntimeException{
        List<ModuloCursoDTO> moduloList= moduloService.getModuloDTO();
        if(moduloList.isEmpty()){
            throw new RuntimeException("No members found");
        }

        return moduloList;
    }
    @PostMapping("/create")
    public Modulo create(@RequestBody Modulo modulo){

       Modulo modulo1= moduloService.save(modulo);

        return modulo1;
    }

    @DeleteMapping("/delete/{idModulo}")
    public ResponseEntity<Object> delete(@PathVariable String idModulo ) throws RuntimeException  {
       moduloService.delete(idModulo);
        return ResponseEntity.noContent().build();

    }


    @GetMapping("/get/{idModulo}")
    public Modulo find(@PathVariable String idModulo) throws  RuntimeException{
       Modulo modulo= moduloService.find(idModulo);
        if (modulo == null) {
            throw new RuntimeException("Member with ID " + idModulo + " not found");
        }
        return modulo;
    }

    @PutMapping("/{idModulo}")
    public Modulo edit(@PathVariable String idModulo, @RequestBody Modulo modulo) throws  RuntimeException{
        Modulo existingMo = moduloService.find(idModulo); // Check for existing member before edit
        if (existingMo == null) {
            throw new RuntimeException("Member with ID " + idModulo + " not found");
        }
       Modulo modEdited =  moduloService.edit(idModulo, modulo);
        return modEdited;

    }
}
