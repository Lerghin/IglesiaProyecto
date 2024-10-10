package com.academia.iglesia.controller;

import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Grupo;
import com.academia.iglesia.service.ICursoService;
import com.academia.iglesia.service.IGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupo")
public class GrupoController {
    @Autowired
    private IGrupoService grupoService;

    @GetMapping("/get")
    public List<Grupo> getSales() throws  RuntimeException{
        List<Grupo> grupoList= grupoService.get();
        if(grupoList.isEmpty()){
            throw new RuntimeException("No members found");
        }

        return grupoList;
    }
    @PostMapping("/create")
    public Grupo create(@RequestBody Grupo grupo){

        Grupo grupo1= grupoService.save(grupo);

        return grupo1;
    }

    @DeleteMapping("/delete/{idGrupo}")
    public ResponseEntity<Object> delete(@PathVariable String idGrupo) throws RuntimeException  {
        grupoService.delete(idGrupo);
        return ResponseEntity.noContent().build();

    }


    @GetMapping("/get/{id}")
    public Grupo find(@PathVariable String idGrupo) throws  RuntimeException{
        Grupo grupo= grupoService.find(idGrupo);
        if (grupo == null) {
            throw new RuntimeException("Member with ID " + idGrupo + " not found");
        }
        return grupo;
    }

    @PutMapping("/{id}")
    public Grupo edit(@PathVariable String idGrupo, @RequestBody Grupo grupo) throws  RuntimeException{
        Grupo existingGrupo = grupoService.find(idGrupo); // Check for existing member before edit
        if (existingGrupo == null) {
            throw new RuntimeException("Member with ID " + idGrupo + " not found");
        }
       Grupo grupoEdited = grupoService.edit(idGrupo,grupo);
        return grupoEdited;

    }
}
