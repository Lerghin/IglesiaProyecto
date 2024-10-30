package com.academia.iglesia.controller;

import com.academia.iglesia.dto.GrupoDTO;
import com.academia.iglesia.dto.MiembroDTO;
import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Grupo;
import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.service.ICursoService;
import com.academia.iglesia.service.IGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/grupo")
public class GrupoController {
    @Autowired
    private IGrupoService grupoService;

    @GetMapping("/get")
    public List<GrupoDTO> getSales() throws  RuntimeException{
        List<Grupo> grupoList= grupoService.get();
        if(grupoList.isEmpty()){
            throw new RuntimeException("No members found");
        }
        List<GrupoDTO> grupoListDTO=  new ArrayList<>();

         for(Grupo grupo: grupoList){
             GrupoDTO grupoDTO= new GrupoDTO();
             grupoDTO.setIdGrupo(grupo.getIdGrupo());
             grupoDTO.setNumeroGrupo(grupo.getNumeroGrupo());
             List<MiembroDTO> miembroDTOList= new ArrayList<>();
             for(Miembro miembro: grupo.getMiembroList()){
                 MiembroDTO miembroDTO= new MiembroDTO();
                 miembroDTO.setIdMiembro(miembro.getIdMiembro());
                 miembroDTO.setCedula(miembro.getCedula());
                 miembroDTO.setNombre(miembro.getNombre());
                 miembroDTO.setApellido(miembro.getApellido());
                 miembroDTOList.add(miembroDTO);

             }
             grupoDTO.setMiembroList(miembroDTOList);
             grupoListDTO.add(grupoDTO);
         }


        return grupoListDTO;
    }


    @PostMapping("/create")
    public String create(@RequestBody GrupoDTO grupo){

        Grupo grupo1= grupoService.save(grupo);

        return "Guardado exitosamente";
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
