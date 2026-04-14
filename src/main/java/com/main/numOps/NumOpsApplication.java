package com.main.numOps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.main")
@EnableAsync
public class NumOpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NumOpsApplication.class, args);
	}

}
