package com.academia.iglesia.controller;

import com.academia.iglesia.dto.NotaMiembroDTO;
import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Nota;
import com.academia.iglesia.service.ICursoService;
import com.academia.iglesia.service.INotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/nota")
public class NotaController {
    @Autowired
    private INotaService notaService;

    @GetMapping("/get")
    public List<Nota> get() throws  RuntimeException{
        List<Nota> notaList= notaService.get();
        if(notaList.isEmpty()){
            throw new RuntimeException("No members found");
        }

        return notaList;
    }
    @GetMapping("/getnota")
    public List<NotaMiembroDTO> getDTO() throws  RuntimeException{
        List<NotaMiembroDTO> notaList= notaService.getDTO();
        if(notaList.isEmpty()){
            throw new RuntimeException("No members found");
        }

        return notaList;
    }
    @PostMapping("/create")
    public Nota create(@RequestBody Nota nota){

       Nota nota1= notaService.save(nota);

        return nota1;
    }
    @PostMapping("/createnot")
    public Nota createNotaDTO(@RequestBody NotaMiembroDTO nota){

        Nota nota1= notaService.saveNotaDTO(nota);

        return nota1;
    }

    @DeleteMapping("/delete/{idNota}")
    public String delete(@PathVariable String idNota ) throws RuntimeException  {
        notaService.delete(idNota);
        return "Borrado correctamente";

    }


    @GetMapping("/get/{idNota}")
    public Nota find(@PathVariable String idNota) throws  RuntimeException{
        Nota nota= notaService.find(idNota);
        if (nota == null) {
            throw new RuntimeException("Member with ID " + idNota + " not found");
        }
        return nota;
    }
    @GetMapping("/get/{cedula}/{idModulo}")
    public NotaMiembroDTO findNota(@PathVariable String cedula, @PathVariable String idModulo ) throws  RuntimeException{
        NotaMiembroDTO nota= notaService.findNota(cedula, idModulo);
        if (nota == null) {
            throw new RuntimeException("Member with ID " + cedula + " not found");
        }
        return nota;
    }

    @PutMapping("/{idNota}")
    public Nota edit(@PathVariable String idNota, @RequestBody Nota nota) throws  RuntimeException{
       Nota existingNota =  notaService.find(idNota); // Check for existing member before edit
        if (existingNota == null) {
            throw new RuntimeException("Member with ID " + idNota + " not found");
        }
        Nota notaEdited = notaService.edit(idNota,nota);
        return notaEdited;

    }
}
