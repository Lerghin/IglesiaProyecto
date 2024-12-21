package com.academia.iglesia.controller;

import com.academia.iglesia.dto.ModuloCursoDTO;
import com.academia.iglesia.dto.ModuloNotaDTO;
import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Modulo;
import com.academia.iglesia.service.ICursoService;
import com.academia.iglesia.service.IModuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "https://dashboard-academy-church.vercel.app/")
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

    @GetMapping("/getmodulodto/{idCurso}")
    public ModuloCursoDTO getMDTO( @PathVariable  String idCurso) throws  RuntimeException{
        ModuloCursoDTO modulo= moduloService.ModuloGetDTO(idCurso);
        if (modulo != null) {
            return modulo;
        }
        throw new RuntimeException("No members found");

    }

    @GetMapping("/getmodulos")
    public List<ModuloNotaDTO> getMDTONOTA() throws  RuntimeException{
        List<ModuloNotaDTO> moduloList= moduloService.getModuloDTO();
        if(moduloList.isEmpty()){
            throw new RuntimeException("No members found");
        }

        return moduloList;
    }
    @PostMapping("/create/{idCurso}")
    public String create(@RequestBody Modulo modulo, @PathVariable String idCurso){

        Modulo modulo1= moduloService.save(modulo, idCurso);


        return "creado con exito";
    }



    @DeleteMapping("/delete/{idModulo}")
    public String delete(@PathVariable String idModulo)   {
        moduloService.delete(idModulo);
        return "Eliminado Correctamente";

    }
    @PutMapping("/delete/{idModulo}/{idCurso}")
    public String editCurso(@PathVariable String idModulo, @PathVariable String idCurso) throws  RuntimeException{
        Modulo modulo=this.find(idModulo);
        moduloService.editCursoModulo(idCurso, modulo);
        delete(idModulo);
        return "Satisfactoriamente" ;

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
