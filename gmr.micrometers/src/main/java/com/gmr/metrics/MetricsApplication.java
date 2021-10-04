package com.gmr.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class MetricsApplication {

	public static void main(String[] args) {
		MeterRegistry registry=new SimpleMeterRegistry();
		
		Counter counter = registry.counter("numero.empleados", "oficina", "Pepito Perez");
		counter.increment();
		counter.increment(200);
		
		System.out.printf("Número de empleados %f", counter.count());
	}
}
