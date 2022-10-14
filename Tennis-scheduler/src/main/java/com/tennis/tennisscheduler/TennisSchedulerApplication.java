package com.tennis.tennisscheduler;

import com.tennis.tennisscheduler.config.StartupSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TennisSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TennisSchedulerApplication.class, args);
		StartupSwagger.launchBrowser();
	}

}
