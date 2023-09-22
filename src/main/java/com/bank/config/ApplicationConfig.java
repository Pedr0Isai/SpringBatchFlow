package com.bank.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class ApplicationConfig {

	/**
	 * Configuración de la base de datos, para que funcione no olvides agregar
	 * "@SpringBootApplication (exclude={DataSourceAutoConfiguration.class})" en la
	 * clase {App}Application
	 */
	@Bean
	DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:file:C:/h2db/batchFlow");
		dataSource.setUsername("sa");
		dataSource.setPassword("sa");
		return dataSource;
	}
}
