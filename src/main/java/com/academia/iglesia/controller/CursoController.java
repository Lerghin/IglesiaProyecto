package com.academia.iglesia.controller;

import com.academia.iglesia.dto.CursoDTO;
import com.academia.iglesia.model.Curso;
import com.academia.iglesia.service.ICursoService;
import com.academia.iglesia.service.IProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "https://dashboard-academy-church.vercel.app/")
@RestController
@RequestMapping("/curso")

public class CursoController {
    @Autowired
    private ICursoService cursoService;
    @Autowired
    private IProfessorService professorService;

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
    public CursoDTO find(@PathVariable String idCurso) throws  RuntimeException{
        CursoDTO curso= cursoService.findDTOCurso(idCurso);
        if (curso == null) {
            throw new RuntimeException("Member with ID " + idCurso + " not found");
        }
        return curso;
    }
    @GetMapping("/getCedulas/{idCurso}")
    public ResponseEntity<Map<String, String>> getCedulas(@PathVariable String idCurso) {
        Map<String, String> cedulasNombres = cursoService.cedulasCursos(idCurso);
        return ResponseEntity.ok(cedulasNombres);
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

    @PutMapping("/{cedula}/{idCurso}")
    public String addProfCurso(@PathVariable String cedula,@PathVariable String idCurso) throws  RuntimeException{
        Curso existingCurso = cursoService.find(idCurso); // Check for existing member before edit
        if (existingCurso == null) {
            throw new RuntimeException("Member with ID " + idCurso + " not found");
        }
        professorService.addProfesorCurso(cedula, idCurso); ;
        return "Profesor agregado exitosamente";

    }
    @PutMapping("/delete/{cedula}/{idCurso}")
    public String removeProfCurso(@PathVariable String cedula,@PathVariable String idCurso) throws  RuntimeException{
        Curso existingCurso = cursoService.find(idCurso); // Check for existing member before edit
        if (existingCurso == null) {
            throw new RuntimeException("Member with ID " + idCurso + " not found");
        }
        professorService.removeProfessor(cedula, idCurso); ;
        return "Profesor removido exitosamente";

    }


    @PutMapping("/add-member/{idCurso}/{cedula}")
    public String addMemberCurso(@PathVariable String idCurso,@PathVariable String cedula) throws  RuntimeException{
        Curso existingCurso = cursoService.find(idCurso); // Check for existing member before edit
        if (existingCurso == null) {
            throw new RuntimeException("Member with ID " + idCurso + " not found");
        }
        cursoService.addMemberCurso(idCurso, cedula); ;
        return "Miembro agregado exitosamente";

    }
    @PutMapping("/delete-member/{idCurso}/{cedula}")
    public String removeMemberCurso(@PathVariable String cedula,@PathVariable String idCurso) throws  RuntimeException{
        Curso existingCurso = cursoService.find(idCurso); // Check for existing member before edit
        if (existingCurso == null) {
            throw new RuntimeException("Member with ID " + idCurso + " not found");
        }
        cursoService.removeMemberCurso(idCurso,cedula); ;
        return "Profesor removido exitosamente";

    }
}
