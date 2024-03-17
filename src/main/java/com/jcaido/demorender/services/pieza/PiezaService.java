package com.jcaido.demorender.services.pieza;

import com.jcaido.demorender.DTOs.pieza.PiezaCrearDTO;
import com.jcaido.demorender.DTOs.pieza.PiezaDTO;

import java.util.List;

public interface PiezaService {

    PiezaDTO crearPieza(PiezaCrearDTO piezaCrearDTO);
    List<PiezaDTO> findAll();
    PiezaDTO findById(Long id);
    PiezaDTO findByReferencia(String referencia);
    List<PiezaDTO> findByNombre (String nombre);
    PiezaDTO modificarPieza(PiezaDTO piezaDTO);
    String deleteById(Long id);
}
