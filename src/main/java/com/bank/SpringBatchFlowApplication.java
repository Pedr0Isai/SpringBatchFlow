package com.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (exclude={DataSourceAutoConfiguration.class})
public class SpringBatchFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchFlowApplication.class, args);
	}

}
