package com.qltv.QLTV;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.qltv.QLTV.Entity")
public class QltvApplication {
	public static void main(String[] args) {
		SpringApplication.run(QltvApplication.class, args);
	}

}
