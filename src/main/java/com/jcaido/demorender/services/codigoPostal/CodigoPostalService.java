package com.jcaido.demorender.services.codigoPostal;

import com.jcaido.demorender.DTOs.codigoPostal.CodigoPostalCrearDTO;
import com.jcaido.demorender.DTOs.codigoPostal.CodigoPostalDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CodigoPostalService {

    CodigoPostalDTO crearCodigoPostal(CodigoPostalCrearDTO codigoPostalCrearDTO);

    List<CodigoPostalDTO> findAll();

    Page<CodigoPostalDTO> findAllPageable(int page, int size);

    CodigoPostalDTO findById(Long id);

    CodigoPostalDTO findByCodigo(String codigo);

    List<CodigoPostalDTO> findByProvincia (String provincia);

    CodigoPostalDTO findByLocalidad(String localidad);

    String deleteById(Long id);

    CodigoPostalDTO modificarCodigoPostal(CodigoPostalDTO codigoPostalDTO);
}
