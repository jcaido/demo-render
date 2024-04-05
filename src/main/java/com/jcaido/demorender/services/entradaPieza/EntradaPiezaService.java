package com.jcaido.demorender.services.entradaPieza;

import com.jcaido.demorender.DTOs.entradaPieza.EntradaPiezaBusquedasDTO;
import com.jcaido.demorender.DTOs.entradaPieza.EntradaPiezaCrearDTO;
import com.jcaido.demorender.DTOs.entradaPieza.EntradaPiezaDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EntradaPiezaService {

    EntradaPiezaDTO crearEntradaPieza(EntradaPiezaCrearDTO entradaPiezaCrearDTO, Long idPieza, Long idAlbaranProvedor);
    List<EntradaPiezaBusquedasDTO> findAll();
    ResponseEntity<EntradaPiezaBusquedasDTO> findById(Long id);
    List<EntradaPiezaBusquedasDTO> obtenerEntradasPorPiezaHQL(Long id_pieza);
    List<EntradaPiezaBusquedasDTO> obtenerEntradasPiezasPorAlbaranProveedorHQL(Long id_pieza);
    EntradaPiezaDTO modificarEntradaPieza(EntradaPiezaDTO entradaPiezaDTO, Long idPieza);
    String deleteById(Long id);
}
