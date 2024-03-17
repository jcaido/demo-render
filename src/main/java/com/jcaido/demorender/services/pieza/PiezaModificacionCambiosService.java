package com.jcaido.demorender.services.pieza;

import com.jcaido.demorender.DTOs.pieza.PiezaDTO;
import com.jcaido.demorender.repositories.PiezaRepository;
import org.springframework.stereotype.Service;

@Service
public class PiezaModificacionCambiosService {

    private final PiezaDTO piezaDTO;
    private final PiezaRepository piezaRepository;

    public PiezaModificacionCambiosService(PiezaDTO piezaDTO, PiezaRepository piezaRepository) {
        this.piezaDTO = piezaDTO;
        this.piezaRepository = piezaRepository;
    }

    public boolean isReferenciaHaCambiado(PiezaDTO piezaDTO) {

        String referencia = piezaRepository.findById(piezaDTO.getId()).get().getReferencia();

        if (referencia.equals(piezaDTO.getReferencia()))
            return false;

        return true;
    }
}
