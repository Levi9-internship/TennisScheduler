package com.tennis.tennisscheduler;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(name = "Authorization", scheme = "bearer", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER)
public class TennisSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TennisSchedulerApplication.class, args);
	}

}
