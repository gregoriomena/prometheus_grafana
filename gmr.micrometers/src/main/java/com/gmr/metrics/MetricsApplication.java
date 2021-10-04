package com.gmr.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class MetricsApplication {

	public static void main(String[] args) {
		
		CompositeMeterRegistry composite = new CompositeMeterRegistry();
		
		Counter counter = composite.counter("numero.empleados", "oficina", "Pepito Perez");
		counter.increment();
		counter.increment(200);
		
		/*
		 * Incrementos son operaciones que no hacen nada hasta que hay un MeterRegistry en el
		 * CompositeMeterRegistry, por lo que la salida anterior imprimir� 0.
		 */
		System.out.printf("N�mero de empleados %f\n", counter.count());
		
		MeterRegistry registry=new SimpleMeterRegistry();
		
		composite.add(registry);
		
		counter.increment();
		counter.increment(200);

		// Ahora s� mostrar� el 201
		System.out.printf("N�mero de empleados %f", counter.count());
	}
}
