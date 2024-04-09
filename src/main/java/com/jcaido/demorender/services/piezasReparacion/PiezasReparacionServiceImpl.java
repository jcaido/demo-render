package com.jcaido.demorender.services.piezasReparacion;

import com.jcaido.demorender.DTOs.piezasReparacion.PiezasReparacionBusquedasDTO;
import com.jcaido.demorender.DTOs.piezasReparacion.PiezasReparacionBusquedasParcialDTO;
import com.jcaido.demorender.DTOs.piezasReparacion.PiezasReparacionCrearDTO;
import com.jcaido.demorender.DTOs.piezasReparacion.PiezasReparacionDTO;
import com.jcaido.demorender.exceptions.ResourceNotFoundException;
import com.jcaido.demorender.models.OrdenReparacion;
import com.jcaido.demorender.models.Pieza;
import com.jcaido.demorender.models.PiezasReparacion;
import com.jcaido.demorender.repositories.OrdenReparacionRepository;
import com.jcaido.demorender.repositories.PiezaRepository;
import com.jcaido.demorender.repositories.PiezasReparacionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PiezasReparacionServiceImpl implements PiezasReparacionService {

    private final PiezasReparacionRepository piezasReparacionRepository;
    private final OrdenReparacionRepository ordenReparacionRepository;
    private final PiezaRepository piezaRepository;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    public PiezasReparacionServiceImpl(PiezasReparacionRepository piezasReparacionRepository, OrdenReparacionRepository ordenReparacionRepository, PiezaRepository piezaRepository, ModelMapper modelMapper, EntityManager entityManager) {
        this.piezasReparacionRepository = piezasReparacionRepository;
        this.ordenReparacionRepository = ordenReparacionRepository;
        this.piezaRepository = piezaRepository;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
    }

    @Override
    public PiezasReparacionDTO crearPiezasReparacion(PiezasReparacionCrearDTO piezasReparacionCrearDTO, Long id_ordenReparacion, Long id_pieza) {

        if (!ordenReparacionRepository.existsById(id_ordenReparacion))
            throw new ResourceNotFoundException("orden de reparación", "id", String.valueOf(id_ordenReparacion));

        if (!piezaRepository.existsById(id_pieza))
            throw new ResourceNotFoundException("pieza", "id", String.valueOf(id_ordenReparacion));

        PiezasReparacion piezasReparacion = modelMapper.map(piezasReparacionCrearDTO, PiezasReparacion.class);
        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(id_ordenReparacion).get();
        Pieza pieza = piezaRepository.findById(id_pieza).get();
        piezasReparacion.setOrdenReparacion(ordenReparacion);
        piezasReparacion.setPieza(pieza);
        piezasReparacionRepository.save(piezasReparacion);
        PiezasReparacionDTO piezasReparacionRespuesta = modelMapper.map(piezasReparacion, PiezasReparacionDTO.class);

        return piezasReparacionRespuesta;
    }

    @Override
    public List<PiezasReparacionBusquedasDTO> findAll() {
        List<PiezasReparacion> piezasReparacion = piezasReparacionRepository.findAll();

        return  piezasReparacion.stream().map(piezaReparacion-> modelMapper.map(piezaReparacion, PiezasReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public PiezasReparacionBusquedasDTO findById(Long id) {
        Optional<PiezasReparacion> piezaReparacion = piezasReparacionRepository.findById(id);

        if (!piezaReparacion.isPresent())
            throw new ResourceNotFoundException("Pieza-reparacion", "id", String.valueOf(id));

        PiezasReparacionBusquedasDTO piezasReparacionEncontrada = modelMapper.map(piezaReparacion, PiezasReparacionBusquedasDTO.class);

        return piezasReparacionEncontrada;
    }

    @Override
    public List<PiezasReparacionBusquedasParcialDTO> obtenerPiezasReparacionPorOrdenReparacion(Long id) {
        Optional<OrdenReparacion> ordenReparacion = ordenReparacionRepository.findById(id);

        if (!ordenReparacion.isPresent())
            throw new ResourceNotFoundException("Orden de reparacion", "id", String.valueOf(id));

        List<PiezasReparacion> piezasReparacion = ordenReparacion.get().getPiezasReparacion();

        return piezasReparacion.stream().map(piezaReparacion -> modelMapper.map(piezaReparacion, PiezasReparacionBusquedasParcialDTO.class)).toList();
    }

    @Override
    public List<PiezasReparacionBusquedasDTO> obtenerPiezasReparacionPorPiezaHQL(Long id_pieza) {
        Query query = entityManager.createQuery("FROM PiezasReparacion p WHERE p.pieza.id = :id" );
        query.setParameter("id", id_pieza);
        List<PiezasReparacion> piezas = query.getResultList();

        return piezas.stream().map(pieza-> modelMapper.map(pieza, PiezasReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public List<PiezasReparacionBusquedasDTO> obtenerPiezasReparacionPorOrdenReparacion(LocalDate fecha) {
        Query query = entityManager.createQuery("FROM PiezasReparacion p WHERE p.ordenReparacion.fechaApertura <= :fecha " +
                "AND p.ordenReparacion.cerrada = false");
        query.setParameter("fecha", fecha);
        List<PiezasReparacion> piezas = query.getResultList();

        return piezas.stream().map(pieza-> modelMapper.map(pieza, PiezasReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public List<PiezasReparacionBusquedasDTO> obtenerPiezasReparacionPorPiezaYOrdenReparacion(String referencia) {
        Query query = entityManager.createQuery("FROM PiezasReparacion p WHERE p.pieza.referencia = :referencia " +
                "AND p.ordenReparacion.cerrada = false");
        query.setParameter("referencia", referencia);
        List<PiezasReparacion> piezas = query.getResultList();

        return piezas.stream().map(pieza-> modelMapper.map(pieza, PiezasReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public String deleteById(Long id) {
        if (!piezasReparacionRepository.existsById(id))
            throw new ResourceNotFoundException("Imputacion de pieza", "id", String.valueOf(id));

        piezasReparacionRepository.deleteById(id);

        String respuesta = "Imputacion de pieza eliminada con exito";

        return respuesta;
    }
}
