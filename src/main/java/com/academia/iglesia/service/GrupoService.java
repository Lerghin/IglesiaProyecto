package com.academia.iglesia.service;

import com.academia.iglesia.dto.GrupoDTO;
import com.academia.iglesia.dto.MiembroDTO;
import com.academia.iglesia.model.Grupo;
import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.repository.ICursoRepository;
import com.academia.iglesia.repository.IGrupoRepository;
import com.academia.iglesia.repository.MiembrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GrupoService  implements  IGrupoService {
    @Autowired
    private IGrupoRepository grupoRepository;
    @Autowired
    private MiembrosRepository miembrosRepository;

    @Override
    public List<Grupo> get() {
        List<Grupo> grupoList = grupoRepository.findAll();
        return grupoList;
    }

    @Override
    public Grupo save(GrupoDTO grupoDTO) {
        List<Miembro> miembroList = new ArrayList<>();
        Grupo grupo = new Grupo();
        grupo.setNumeroGrupo(grupoDTO.getNumeroGrupo());
        for (MiembroDTO miembroDTO : grupoDTO.getMiembroList()) {
            Miembro miembro = miembrosRepository.findByCedula(miembroDTO.getCedula());
            miembroList.add(miembro);
        }
        grupo.setMiembroList(miembroList);
        grupoRepository.save(grupo);
        return grupo;
    }

    @Override
    public void delete(String idGrupo) {
        grupoRepository.deleteById(idGrupo);
    }

    @Override
    public Grupo find(String idGrupo) {
        Grupo grupo = grupoRepository.findById(idGrupo).orElseThrow(null);

        return grupo;
    }

    @Override
    public Grupo edit(String idGrupo, Grupo grupo) {
        Grupo grupoFind = this.find(idGrupo);
        grupoFind.setNumeroGrupo(grupo.getNumeroGrupo());
        grupoFind.setMiembroList(grupoFind.getMiembroList());
        grupoRepository.save(grupoFind);
        return grupoFind;
    }
    @Override
    public Grupo addMiembro(String idGrupo, String cedula) {
        // Buscar el grupo por ID
        Grupo grupo = this.find(idGrupo);

        // Buscar el miembro por cédula
        Miembro miembro = miembrosRepository.findByCedula(cedula);
        if (miembro == null) {
            throw new RuntimeException("No se encontró un miembro con la cédula proporcionada");
        }

        // Validar si el miembro ya está en el grupo
        List<Miembro> miembroList = grupo.getMiembroList();
        boolean miembroExiste = miembroList.stream()
                .anyMatch(m -> m.getCedula().equals(cedula));
        if (miembroExiste) {
            throw new RuntimeException("Ya existe la persona en el grupo");
        }

        // Agregar el miembro a la lista
        miembroList.add(miembro);

        // Guardar los cambios
        grupo.setMiembroList(miembroList);
        grupoRepository.save(grupo);

        return grupo;
    }

    @Override
    public Grupo deleteMiembro(String idGrupo, String idMiembro) {
        // Buscar el grupo por ID
        Grupo grupo = this.find(idGrupo);
        if (grupo == null) {
            throw new IllegalArgumentException("Grupo no encontrado con el ID: " + idGrupo);
        }

        // Buscar el miembro por ID
        Miembro miembro = miembrosRepository.findById(idMiembro)
                .orElseThrow(() -> new IllegalArgumentException("Miembro no encontrado con el ID: " + idMiembro));

        // Obtener la lista de miembros del grupo
        List<Miembro> miembroList = grupo.getMiembroList();

        // Remover al miembro utilizando un filtro
        boolean removed = miembroList.removeIf(miembroFor -> miembroFor.getIdMiembro().equals(miembro.getIdMiembro()));

        if (!removed) {
            throw new IllegalArgumentException("El miembro con ID " + idMiembro + " no pertenece al grupo " + idGrupo);
        }

        // Actualizar la lista de miembros y guardar el grupo
        grupo.setMiembroList(miembroList);
        grupoRepository.save(grupo);

        return grupo;
    }

}
