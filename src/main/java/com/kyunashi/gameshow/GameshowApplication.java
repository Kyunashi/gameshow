package com.kyunashi.gameshow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GameshowApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameshowApplication.class, args);

	}






}
