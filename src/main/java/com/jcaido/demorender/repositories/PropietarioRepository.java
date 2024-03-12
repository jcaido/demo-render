package com.jcaido.demorender.repositories;

import com.jcaido.demorender.models.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, Long> {

    @Transactional(readOnly = true)
    Optional<Propietario> findByDni(String dni);

    @Transactional(readOnly = true)
    List<Propietario> findByNombre(String nombre);

    @Transactional(readOnly = true)
    List<Propietario> findByPrimerApellido(String primerApellido);

    @Transactional(readOnly = true)
    List<Propietario> findByNombreAndPrimerApellidoAndSegundoApellido(String nombre, String primerApellido, String segundoApellido);

    @Transactional(readOnly = true)
    boolean existsById(Long id);

    @Transactional(readOnly = true)
    boolean existsByDni(String dni);
}
