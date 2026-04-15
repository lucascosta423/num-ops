package com.main.numOps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication(scanBasePackages = "com.main")
@EnableAsync
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class NumOpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NumOpsApplication.class, args);
	}

}

