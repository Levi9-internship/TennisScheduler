package com.tennis.tennisscheduler;

import com.tennis.tennisscheduler.config.StartupSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class TennisSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TennisSchedulerApplication.class, args);
		StartupSwagger.launchBrowser();
	}

	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}
