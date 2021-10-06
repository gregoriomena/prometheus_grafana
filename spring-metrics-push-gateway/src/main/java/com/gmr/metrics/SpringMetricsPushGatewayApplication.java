package com.gmr.metrics;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMetricsPushGatewayApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SpringMetricsPushGatewayApplication.class, args);
	}

}
