package com.hospital;

import com.hospital.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(WebConfig.class)
@SpringBootApplication
public class AdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}
}
