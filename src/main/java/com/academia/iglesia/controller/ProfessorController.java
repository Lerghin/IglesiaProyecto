package com.academia.iglesia.controller;

import com.academia.iglesia.model.Pago;
import com.academia.iglesia.model.Professor;
import com.academia.iglesia.service.IProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000/")
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
    public String create(@RequestBody Professor pro){
         List<Professor> professorList= this.get();
         for(Professor professor: professorList){
             if(pro.getCedula().equalsIgnoreCase(professor.getCedula())){
                 throw new ResponseStatusException(HttpStatus.CONFLICT, "La cédula ya está registrada");
             }
             else{
                Professor professorSaved= profeServ.save(pro);

             }

         }
        return  "Registrado con exito";
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

    @GetMapping("/get/number")
    public Integer miembrosNumber() throws  RuntimeException{
        int count= profeServ.countProfessor();
        return count;
    }



}
