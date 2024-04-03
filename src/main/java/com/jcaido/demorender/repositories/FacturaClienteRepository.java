package com.jcaido.demorender.repositories;

import com.jcaido.demorender.models.FacturaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaClienteRepository extends JpaRepository<FacturaCliente, Long> {
}
