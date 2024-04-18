package com.jcaido.demorender.services.ordenReparacion;

import com.jcaido.demorender.DTOs.ordenReparacion.*;
import com.jcaido.demorender.exceptions.BadRequestCreacionException;
import com.jcaido.demorender.exceptions.BadRequestModificacionException;
import com.jcaido.demorender.exceptions.ResourceNotFoundException;
import com.jcaido.demorender.models.ManoDeObra;
import com.jcaido.demorender.models.OrdenReparacion;
import com.jcaido.demorender.models.Vehiculo;
import com.jcaido.demorender.repositories.ManoDeObraRepository;
import com.jcaido.demorender.repositories.OrdenReparacionRepository;
import com.jcaido.demorender.repositories.PiezaRepository;
import com.jcaido.demorender.repositories.VehiculoRepository;
import com.jcaido.demorender.services.piezasReparacion.PiezasReparacionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenReparacionServiceImpl implements OrdenReparacionService {

    private final OrdenReparacionRepository ordenReparacionRepository;
    private final VehiculoRepository vehiculoRepository;
    private final PiezaRepository piezaRepository;
    private final ManoDeObraRepository manoDeObraRepository;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;
    private final OrdenReparacionModificacionCambiosService ordenReparacionModificacionCambiosService;
    private final PiezasReparacionService piezasReparacionService;

    public OrdenReparacionServiceImpl(OrdenReparacionRepository ordenReparacionRepository, VehiculoRepository vehiculoRepository, PiezaRepository piezaRepository, ManoDeObraRepository manoDeObraRepository, EntityManager entityManager, ModelMapper modelMapper, OrdenReparacionModificacionCambiosService ordenReparacionModificacionCambiosService, PiezasReparacionService piezasReparacionService) {
        this.ordenReparacionRepository = ordenReparacionRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.piezaRepository = piezaRepository;
        this.manoDeObraRepository = manoDeObraRepository;
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.ordenReparacionModificacionCambiosService = ordenReparacionModificacionCambiosService;
        this.piezasReparacionService = piezasReparacionService;
    }

    @Override
    public OrdenReparacionDTO crearOrdenReparacion(OrdenReparacionCrearDTO ordenReparacionCrearDTO, Long idVehiculo) {

        if (!ordenReparacionModificacionCambiosService.validacionVehiculo(idVehiculo))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El vehiculo asociado a la orden de reparacion no existe");

        OrdenReparacion ordenReparacion = modelMapper.map(ordenReparacionCrearDTO, OrdenReparacion.class);
        Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo).get();
        ordenReparacion.setVehiculo(vehiculo);
        ordenReparacionRepository.save(ordenReparacion);
        OrdenReparacionDTO ordenReparacionRespuesta = modelMapper.map(ordenReparacion, OrdenReparacionDTO.class);

        return ordenReparacionRespuesta;
    }

    @Override
    public List<OrdenReparacionBusquedasDTO> findAll() {
        List<OrdenReparacion> ordenes = ordenReparacionRepository.findAll();

        return ordenes.stream().map(orden-> modelMapper.map(orden, OrdenReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public OrdenReparacionBusquedasDTO findById(Long id) {
        Optional<OrdenReparacion> ordenReparacion = ordenReparacionRepository.findById(id);

        if (!ordenReparacion.isPresent())
            throw new ResourceNotFoundException("Orden de reparacion", "id", String.valueOf(id));

        OrdenReparacionBusquedasDTO ordenReparacionEncontrada = modelMapper.map(ordenReparacion, OrdenReparacionBusquedasDTO.class);

        return ordenReparacionEncontrada;
    }

    @Override
    public OrdenReparacionBusquedasParcialDTO findByIdParcial(Long id) {
        Optional<OrdenReparacion> ordenReparacion = ordenReparacionRepository.findById(id);

        if (!ordenReparacion.isPresent())
            throw new ResourceNotFoundException("Orden de reparacion", "id", String.valueOf(id));

        OrdenReparacionBusquedasParcialDTO ordenReparacionEncontrada = modelMapper.map(ordenReparacion, OrdenReparacionBusquedasParcialDTO.class);

        return ordenReparacionEncontrada;
    }

    @Override
    public List<OrdenReparacionBusquedasDTO> findByFechaApertura(LocalDate fechaApertura) {
        List<OrdenReparacion> ordenesReparacion = ordenReparacionRepository.findByFechaApertura(fechaApertura);

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public List<OrdenReparacionBusquedasDTO> findByFechaCierre(LocalDate fechaCierre) {
        List<OrdenReparacion> ordenesReparacion = ordenReparacionRepository.findByFechaCierre(fechaCierre);

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public List<OrdenReparacionBusquedasDTO> findByCerrada(Boolean cerrada) {
        List<OrdenReparacion> ordenesReparacion = ordenReparacionRepository.findByCerrada(cerrada);

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public List<OrdenReparacionBusquedasParcialDTO> findByCerradaParcial(Boolean cerrada) {
        List<OrdenReparacion> ordenesReparacion = ordenReparacionRepository.findByCerrada(cerrada);

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasParcialDTO.class)).toList();
    }

    @Override
    public List<OrdenReparacionBusquedasParcialDTO> findByCerradaParcialByFechaAperturaAsc(Boolean cerrada) {
        List<OrdenReparacion> ordenesReparacion = ordenReparacionRepository.findByCerradaOrderByFechaAperturaAsc(cerrada);

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasParcialDTO.class)).toList();
    }

    @Override
    public List<OrdenReparacionBusquedasParcialDTO> findByCerradaParcialPorFechaApertura(Boolean cerrada, LocalDate fechaApertura) {
        Query query = entityManager.createQuery("FROM OrdenReparacion o WHERE o.cerrada = :cerrada AND o.fechaApertura = :fechaApertura");
        query.setParameter("cerrada", cerrada);
        query.setParameter("fechaApertura", fechaApertura);
        List<OrdenReparacion> ordenesReparacion = query.getResultList();

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasParcialDTO.class)).toList();
    }

    @Override
    public List<OrdenReparacionBusquedasDTO> findByCerradaEntreFechasDeCierre(Boolean cerrada, LocalDate fechaCierreInicical, LocalDate fechaCierreFinal) {
        Query query = entityManager.createQuery("FROM OrdenReparacion o " +
                "WHERE o.cerrada = :cerrada AND o.fechaCierre >= :fechaCierreInicial AND o.fechaCierre <= :fechaCierreFinal");
        query.setParameter("cerrada", cerrada);
        query.setParameter("fechaCierreInicial", fechaCierreInicical);
        query.setParameter("fechaCierreFinal", fechaCierreFinal);
        List<OrdenReparacion> ordenesReparacion = query.getResultList();

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public List<OrdenReparacionBusquedasParcialDTO> findByCerradaParcialPorVehiculo(Boolean cerrada, Long id_vehiculo) {
        Query query = entityManager.createQuery("FROM OrdenReparacion o WHERE o.cerrada = :cerrada AND o.vehiculo.id = :id");
        query.setParameter("cerrada", cerrada);
        query.setParameter("id", id_vehiculo);
        List<OrdenReparacion> ordenesReparacion = query.getResultList();

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasParcialDTO.class)).toList();
    }

    @Override
    public List<OrdenReparacionBusquedasDTO> findByCerradaPorVehiculo(Boolean cerrada, Long id_vehiculo) {
        Query query = entityManager.createQuery("FROM OrdenReparacion o WHERE o.cerrada = :cerrada AND o.vehiculo.id = :id ORDER BY o.fechaCierre desc");
        query.setParameter("cerrada", cerrada);
        query.setParameter("id", id_vehiculo);
        List<OrdenReparacion> ordenesReparacion = query.getResultList();

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorVehiculo(Long id_vehiculo) {
        Query query = entityManager.createQuery("FROM OrdenReparacion o WHERE o.vehiculo.id = :id");
        query.setParameter("id", id_vehiculo);
        List<OrdenReparacion> ordenesReparacion = query.getResultList();

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public List<OrdenReparacionReducidaDTO> obtenerOrdenesReparacionCerradasPtesFacturar() {
        Query query = entityManager.createQuery("FROM OrdenReparacion o WHERE o.cerrada = :cerrada "+
                "AND o.facturada = :facturada");
        query.setParameter("cerrada", true);
        query.setParameter("facturada", false);
        List<OrdenReparacion> ordenesReparacion = query.getResultList();

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionReducidaDTO.class)).toList();
    }

    @Override
    public OrdenReparacionDTO modificarOrdenReparacion(OrdenReparacionDTO ordenReparacionDTO, Long id_vehiculo) {
        if (ordenReparacionDTO.getId() == null)
            throw new BadRequestModificacionException("Orden de reparacion", "id");

        if (!ordenReparacionRepository.existsById(ordenReparacionDTO.getId()))
            throw new ResourceNotFoundException("Orden de reparacion", "id", String.valueOf(ordenReparacionDTO.getId()));

        if (!ordenReparacionModificacionCambiosService.validacionVehiculo(id_vehiculo))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El vehiculo asociado a la orden de reparacion no existe");

        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(ordenReparacionDTO.getId()).get();
        Vehiculo vehiculo = vehiculoRepository.findById(id_vehiculo).get();

        ordenReparacion.setFechaApertura(ordenReparacionDTO.getFechaApertura());
        ordenReparacion.setDescripcion(ordenReparacionDTO.getDescripcion());
        ordenReparacion.setKilometros(ordenReparacionDTO.getKilometros());
        ordenReparacion.setVehiculo(vehiculo);

        OrdenReparacion ordenReparacionModificada = ordenReparacionRepository.save(ordenReparacion);
        OrdenReparacionDTO ordenReparacionModificadaDTO = modelMapper.map(ordenReparacion, OrdenReparacionDTO.class);

        return ordenReparacionModificadaDTO;
    }

    @Override
    public OrdenReparacionDTO modificarOrdenReparacionHoras(OrdenReparacionHorasDTO ordenReparacionHorasDTO) {
        if (ordenReparacionHorasDTO.getId() == null)
            throw new BadRequestModificacionException("Orden de reparacion", "id");

        if (!ordenReparacionRepository.existsById(ordenReparacionHorasDTO.getId()))
            throw new ResourceNotFoundException("Orden de reparacion", "id", String.valueOf(ordenReparacionHorasDTO.getId()));

        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(ordenReparacionHorasDTO.getId()).get();

        ordenReparacion.setHoras(ordenReparacionHorasDTO.getHoras());

        OrdenReparacion ordenReparacionModificada = ordenReparacionRepository.save(ordenReparacion);
        OrdenReparacionDTO ordenReparacionModificadaDTO = modelMapper.map(ordenReparacion, OrdenReparacionDTO.class);

        return ordenReparacionModificadaDTO;
    }

    @Override
    public OrdenReparacionDTO modificarOrdenReparacionCierre(OrdenReparacionCierreDTO ordenReparacionCierreDTO) {
        if (ordenReparacionCierreDTO.getId() == null)
            throw new BadRequestModificacionException("Orden de reparacion", "id");

        if (!ordenReparacionRepository.existsById(ordenReparacionCierreDTO.getId()))
            throw new ResourceNotFoundException("Orden de reparacion", "id", String.valueOf(ordenReparacionCierreDTO.getId()));

        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(ordenReparacionCierreDTO.getId()).get();

        ordenReparacion.setFechaCierre(ordenReparacionCierreDTO.getFechaCierre());
        ordenReparacion.setCerrada(true);

        ManoDeObra manoDeObraActual = manoDeObraRepository.findByPrecioHoraClienteTallerActual(true).get();
        ordenReparacion.setManoDeObra(manoDeObraActual);

        OrdenReparacion ordenReparacionModificada = ordenReparacionRepository.save(ordenReparacion);
        OrdenReparacionDTO ordenReparacionModificadaDTO = modelMapper.map(ordenReparacion, OrdenReparacionDTO.class);

        return ordenReparacionModificadaDTO;
    }

    @Override
    public OrdenReparacionDTO modificarOrdenReparacionAbrir(OrdenReparacionCierreDTO ordenReparacionCierreDTO) {
        if (ordenReparacionCierreDTO.getId() == null)
            throw new BadRequestModificacionException("Orden de reparacion", "id");

        if (!ordenReparacionRepository.existsById(ordenReparacionCierreDTO.getId()))
            throw new ResourceNotFoundException("Orden de reparacion", "id", String.valueOf(ordenReparacionCierreDTO.getId()));

        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(ordenReparacionCierreDTO.getId()).get();

        ordenReparacion.setFechaCierre(ordenReparacionCierreDTO.getFechaCierre());
        ordenReparacion.setCerrada(false);

        OrdenReparacion ordenReparacionModificada = ordenReparacionRepository.save(ordenReparacion);
        OrdenReparacionDTO ordenReparacionModificadaDTO = modelMapper.map(ordenReparacion, OrdenReparacionDTO.class);

        return ordenReparacionModificadaDTO;
    }

    @Override
    public String deleteById(Long id) {
        Optional<OrdenReparacion> ordenReparacion = ordenReparacionRepository.findById(id);

        if (!ordenReparacion.isPresent())
            throw new ResourceNotFoundException("Orden de reparacion", "id", String.valueOf(id));

        if (ordenReparacion.get().getCerrada())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La orden de reparacion esta cerrada");

        //if (piezasReparacionService.obtenerPiezasReparacionPorOrdenReparacion(id).size() > 0)
        //    throw new ResponseStatusException(HttpStatus.CONFLICT, "Existen piezas relacionadas con esa orden de reparacion");

        ordenReparacionRepository.deleteById(id);
        String respuesta = "Orden de reparacion eliminada con exito";

        return respuesta;
    }
}
