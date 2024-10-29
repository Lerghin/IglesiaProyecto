package com.academia.iglesia.controller;

import com.academia.iglesia.model.Pago;
import com.academia.iglesia.model.Professor;
import com.academia.iglesia.service.IProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profe")
public class ProfessorController {

@Autowired
private IProfessorService profeServ;


    @GetMapping("/get")
    public List<Professor> get() throws  RuntimeException{
        List<Professor> profes= profeServ.get();
        if(profes.isEmpty()){
            throw new RuntimeException("No members found");
        }

        return profes;
    }
    @PostMapping("/create")
    public Professor create(@RequestBody Professor pro){

        Professor professor= profeServ.save(pro);

        return  professor;
    }

    @DeleteMapping("/delete/{idProfessor}")
    public ResponseEntity<Object> delete(@PathVariable String idProfessor ) throws RuntimeException  {
        profeServ.delete(idProfessor);
        return ResponseEntity.noContent().build();

    }


    @GetMapping("/get/{idProfessor}")
    public Professor find(@PathVariable String idProfessor) throws  RuntimeException{
       Professor professor= profeServ.find(idProfessor);
        if (professor == null) {
            throw new RuntimeException("Member with ID " + idProfessor + " not found");
        }
        return professor;
    }

    @PutMapping("/{idProfessor}")
    public Professor edit(@PathVariable String idProfessor, @RequestBody Professor pro) throws  RuntimeException{

       Professor profEdited = profeServ.edit(idProfessor,pro);
        return profEdited;

    }



    @GetMapping("/{cedula}")
    public Professor findByCedula(@PathVariable String cedula) throws  RuntimeException{

        Professor prof = profeServ.findByCedula(cedula);
        return prof;

    }


}
