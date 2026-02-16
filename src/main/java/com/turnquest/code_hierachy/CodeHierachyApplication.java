package com.turnquest.code_hierachy;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class CodeHierachyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeHierachyApplication.class, args);
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
}
