package com.ssafy.dmobile;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = {@Server(url = "http://i10d102.p.ssafy.io/", description = "Secure Server URL")})
@SpringBootApplication
public class DmobileApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmobileApplication.class, args);
	}

}
