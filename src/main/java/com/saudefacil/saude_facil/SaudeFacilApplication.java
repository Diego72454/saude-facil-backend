package com.saudefacil.saude_facil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SaudeFacilApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaudeFacilApplication.class, args);
	}

}
