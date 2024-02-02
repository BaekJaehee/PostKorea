package com.ssafy.dmobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.ssafy.dmobile.entity")
public class DmobileApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmobileApplication.class, args);
	}

}
