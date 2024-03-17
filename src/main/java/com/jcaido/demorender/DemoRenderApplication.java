package com.jcaido.demorender;

import com.jcaido.demorender.DTOs.codigoPostal.CodigoPostalDTO;
import com.jcaido.demorender.DTOs.pieza.PiezaDTO;
import com.jcaido.demorender.DTOs.propietario.PropietarioDTO;
import com.jcaido.demorender.DTOs.proveedor.ProveedorDTO;
import com.jcaido.demorender.DTOs.vehiculo.VehiculoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoRenderApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	@Bean
	public CodigoPostalDTO codigoPostalDTO() {
		return new CodigoPostalDTO();
	}
	@Bean
	public PropietarioDTO propietarioDTO() {
		return new PropietarioDTO();
	}
	@Bean
	public ProveedorDTO proveedorDTO() {
		return new ProveedorDTO();
	}
	@Bean
	public VehiculoDTO vehiculoDTO() {
		return new VehiculoDTO();
	}
	@Bean
	public PiezaDTO piezaDTO() {
		return new PiezaDTO();
	}

	public static void main(String[] args) {SpringApplication.run(DemoRenderApplication.class, args);}

}
