package com.academia.iglesia.controller;

import com.academia.iglesia.dto.GrupoDTO;
import com.academia.iglesia.dto.MiembroDTO;
import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Grupo;
import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.service.ICursoService;
import com.academia.iglesia.service.IGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "https://dashboard-academy-church.vercel.app/")
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


    @GetMapping("/get/{idGrupo}")
    public GrupoDTO find(@PathVariable String idGrupo) throws  RuntimeException{
        Grupo grupo= grupoService.find(idGrupo);
        if (grupo == null) {
            throw new RuntimeException("Member with ID " + idGrupo + " not found");
        }
        GrupoDTO grupoDTO= new GrupoDTO();
        grupoDTO.setIdGrupo(grupo.getIdGrupo());
        grupoDTO.setNumeroGrupo(grupo.getNumeroGrupo());
        List<MiembroDTO> miembroDTOList= new ArrayList<>();
        for(Miembro miembro: grupo.getMiembroList()){
        MiembroDTO miembroDTO= new MiembroDTO();
        miembroDTO.setIdMiembro(miembro.getIdMiembro());
        miembroDTO.setNombre(miembro.getNombre());
        miembroDTO.setCedula(miembro.getCedula());
        miembroDTO.setApellido(miembro.getApellido());
        miembroDTOList.add(miembroDTO);

        }
        grupoDTO.setMiembroList(miembroDTOList);
        return grupoDTO;
    }

    @PutMapping("/{idGrupo}")
    public Grupo edit(@PathVariable String idGrupo, @RequestBody Grupo grupo) throws  RuntimeException{
        Grupo existingGrupo = grupoService.find(idGrupo);
        if (existingGrupo == null) {
            throw new RuntimeException("Member with ID " + idGrupo + " not found");
        }
       Grupo grupoEdited = grupoService.edit(idGrupo,grupo);
        return grupoEdited;

    }
    @PutMapping("add-member/{idGrupo}")
    public ResponseEntity<?> addMember(@PathVariable String idGrupo, @RequestParam String cedula) {
        try {
            Grupo existingGrupo = grupoService.find(idGrupo);
            if (existingGrupo == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Grupo con ID " + idGrupo + " no encontrado");
            }
            Grupo grupoEdited = grupoService.addMiembro(idGrupo, cedula);
            return ResponseEntity.ok(grupoEdited);
        } catch (RuntimeException ex) {
            // Enviar mensaje de error claro al frontend
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            // Manejo de cualquier otro error inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error inesperado: " + ex.getMessage());
        }
    }

    @PutMapping("remove-member/{idGrupo}")
    public ResponseEntity<Grupo> removeMember(
            @PathVariable String idGrupo,
            @RequestParam String idMiembro) {

        try {
            // Buscar el grupo existente
            Grupo existingGrupo = grupoService.find(idGrupo);
            if (existingGrupo == null) {
                throw new RuntimeException("Grupo con ID " + idGrupo + " no encontrado");
            }

            // Eliminar al miembro del grupo
            Grupo grupoEdited = grupoService.deleteMiembro(idGrupo, idMiembro);
            return ResponseEntity.ok(grupoEdited); // Retornar el grupo actualizado con código 200

        } catch (RuntimeException e) {
            // Manejo de errores
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Puedes agregar un mensaje de error más descriptivo si es necesario
        }
    }




}
