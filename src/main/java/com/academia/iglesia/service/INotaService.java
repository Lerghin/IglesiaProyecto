package com.academia.iglesia.service;


import com.academia.iglesia.dto.NotaMiembroDTO;
import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Nota;

import java.util.List;

public interface INotaService {
    public List<Nota> get();
    public Nota save(Nota not);
    public void delete(String idNota);
    public Nota find(String  idNota);
    public Nota edit(String idNota, Nota nota);
    public Nota saveNotaDTO(NotaMiembroDTO not);
    public List<NotaMiembroDTO> getDTO();
    public NotaMiembroDTO findNota(String cedula, String idModulo);
    public List<NotaMiembroDTO> getNotasMiembro(String idModulo);
}