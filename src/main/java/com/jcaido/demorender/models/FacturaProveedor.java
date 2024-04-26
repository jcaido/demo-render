package com.jcaido.demorender.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "factura_proveedor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class FacturaProveedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne()
    private Proveedor proveedor;
    @Column(name = "fecha_factura")
    private LocalDate fechaFactura;
    @Column(name = "numero_factura")
    private String numeroFactura;
    @Column(name = "tipo_iva")
    private Integer tipoIVA;
    private Boolean contabilizada = false;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "facturaProveedor")
    private List<AlbaranProveedor> albaranesProveedores = new ArrayList<>();
}
