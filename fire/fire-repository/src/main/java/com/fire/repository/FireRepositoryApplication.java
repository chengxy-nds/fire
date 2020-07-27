package com.fire.repository;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan("com.fire.repository.mapper")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FireRepositoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FireRepositoryApplication.class, args);
	}

}
