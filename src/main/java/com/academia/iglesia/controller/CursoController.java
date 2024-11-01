package com.academia.iglesia.controller;

import com.academia.iglesia.dto.CursoDTO;
import com.academia.iglesia.model.Curso;
import com.academia.iglesia.service.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/curso")

public class CursoController {
    @Autowired
    private ICursoService cursoService;

    @GetMapping("/get")
    public List<Curso> getSales() throws  RuntimeException{
        List<Curso> cursos= cursoService.get();
        if(cursos.isEmpty()){
            throw new RuntimeException("No members found");
        }

        return cursos;
    }
    @GetMapping("/getcur")
    public List<CursoDTO> getCursosDTO() throws  RuntimeException{
        List<CursoDTO> cursos= cursoService.getDTOCurso();
        if(cursos.isEmpty()){
            throw new RuntimeException("No members found");
        }

        return cursos;
    }
    @PostMapping("/create")
    public String create(@RequestBody CursoDTO curso) {
        Curso curso1 = cursoService.save(curso);
        return "Creado Correctamente";
    }


    @DeleteMapping("/delete/{idCurso}")
    public ResponseEntity<Object> delete(@PathVariable String idCurso ) throws RuntimeException  {
       cursoService.delete(idCurso);
        return ResponseEntity.noContent().build();

    }


    @GetMapping("/get/{idCurso}")
    public Curso find(@PathVariable String idCurso) throws  RuntimeException{
        Curso curso= cursoService.find(idCurso);
        if (curso == null) {
            throw new RuntimeException("Member with ID " + idCurso + " not found");
        }
        return curso;
    }

    @PutMapping("/{idCurso}")
    public Curso editMiembro(@PathVariable String idCurso, @RequestBody Curso curso) throws  RuntimeException{
        Curso existingCurso = cursoService.find(idCurso); // Check for existing member before edit
        if (existingCurso == null) {
            throw new RuntimeException("Member with ID " + idCurso + " not found");
        }
        Curso cursoEdited = cursoService.edit(idCurso,curso);
        return cursoEdited;

    }
}
