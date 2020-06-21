package com.example.springredditclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaRepositories
@SpringBootApplication
@EnableAsync
public class SpringRedditCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedditCloneApplication.class, args);
	}

}
