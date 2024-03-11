package com.jcaido.demorender;

import com.jcaido.demorender.DTOs.codigoPostal.CodigoPostalDTO;
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

	public static void main(String[] args) {SpringApplication.run(DemoRenderApplication.class, args);}

}
