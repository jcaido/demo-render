package com.jcaido.demorender;

import com.jcaido.demorender.DTOs.codigoPostal.CodigoPostalDTO;
import com.jcaido.demorender.DTOs.propietario.PropietarioDTO;
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

	public static void main(String[] args) {SpringApplication.run(DemoRenderApplication.class, args);}

}
