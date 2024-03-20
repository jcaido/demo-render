package com.jcaido.demorender.services.manoDeObra;

import com.jcaido.demorender.DTOs.manoDeObra.ManoDeObraCrearDTO;
import com.jcaido.demorender.DTOs.manoDeObra.ManoDeObraDTO;
import com.jcaido.demorender.exceptions.ResourceNotFoundException;
import com.jcaido.demorender.models.ManoDeObra;
import com.jcaido.demorender.repositories.ManoDeObraRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ManoDeObraServiceImpl implements ManoDeObraService {

    private final ManoDeObraRepository manoDeObraRepository;
    private  final ModelMapper modelMapper;

    public ManoDeObraServiceImpl(ManoDeObraRepository manoDeObraRepository, ModelMapper modelMapper) {
        this.manoDeObraRepository = manoDeObraRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ManoDeObraDTO crearManoDeObra(ManoDeObraCrearDTO manoDeObraCrearDTO) {

        if (manoDeObraRepository.existsByprecioHoraClienteTaller(manoDeObraCrearDTO.getPrecioHoraClienteTaller()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El precio horario ya existe");

        // Establecemos todos los precios anteriores a false
        manoDeObraRepository.PrecioHoraActualFalse(false);

        ManoDeObra manoDeObra = modelMapper.map(manoDeObraCrearDTO, ManoDeObra.class);
        manoDeObra.setFechaNuevoPrecio(LocalDate.now());
        manoDeObra.setPrecioHoraClienteTallerActual(true);
        ManoDeObra nuevoPrecioManoDeObra = manoDeObraRepository.save(manoDeObra);
        ManoDeObraDTO manoDeObraRespuesta = modelMapper.map(nuevoPrecioManoDeObra, ManoDeObraDTO.class);

        return manoDeObraRespuesta;
    }

    @Override
    public List<ManoDeObraDTO> findAll() {

        List<ManoDeObra> preciosManoDeObra = manoDeObraRepository.findAllByOrderByFechaNuevoPrecioDesc();

        return  preciosManoDeObra.stream().map(precioManoDeObra-> modelMapper.map(precioManoDeObra, ManoDeObraDTO.class)).toList();
    }

    @Override
    public ManoDeObraDTO findByPrecioHoraClienteTallerActual(Boolean precioHoraClienteTallerActual) {

        Optional<ManoDeObra> manoDeObra = manoDeObraRepository.findByPrecioHoraClienteTallerActual(precioHoraClienteTallerActual);

        if (!manoDeObra.isPresent())
            throw new ResourceNotFoundException("precio de la mano de obra", "id", String.valueOf(precioHoraClienteTallerActual));

        ManoDeObraDTO manoDeObraEncontrada = modelMapper.map(manoDeObra, ManoDeObraDTO.class);

        return manoDeObraEncontrada;
    }
}
