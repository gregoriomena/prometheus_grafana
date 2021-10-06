package com.gmr.metrics;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.exporter.PushGateway;

@SpringBootApplication
public class SpringMetricsPushGatewayApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SpringMetricsPushGatewayApplication.class, args);

		CollectorRegistry registry = new CollectorRegistry();
		Counter students = Counter.build().name("students_total").help("Total students.").register(registry);

		students.inc(150);

		PushGateway pg = new PushGateway("127.0.0.1:9091");
		pg.pushAdd(registry, "gmr_students_job");
	}

}
