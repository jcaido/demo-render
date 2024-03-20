package com.jcaido.demorender.services.manoDeObra;

import com.jcaido.demorender.DTOs.manoDeObra.ManoDeObraCrearDTO;
import com.jcaido.demorender.DTOs.manoDeObra.ManoDeObraDTO;

import java.util.List;

public interface ManoDeObraService {

    ManoDeObraDTO crearManoDeObra(ManoDeObraCrearDTO manoDeObraCrearDTO);
    List<ManoDeObraDTO> findAll();
    ManoDeObraDTO findByPrecioHoraClienteTallerActual(Boolean precioHoraClienteTallerActual);
}
