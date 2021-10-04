package com.gmr.metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class MetricsApplication {

	public static void main(String[] args) {
		
		CompositeMeterRegistry composite = Metrics.globalRegistry;
		
		Counter counter = composite.counter("numero.empleados", "oficina", "Pepito Perez");
		counter.increment();
		counter.increment(200);
		
		/*
		 * Incrementos son operaciones que no hacen nada hasta que hay un MeterRegistry en el
		 * CompositeMeterRegistry, por lo que la salida anterior imprimirá 0.
		 */
		System.out.printf("Número de empleados %f\n", counter.count());
		
		MeterRegistry registry=new SimpleMeterRegistry();
		
		composite.add(registry);
		
		counter.increment();
		counter.increment(200);

		// Ahora sí mostrará el 201
		System.out.printf("Número de empleados %f\n", counter.count());
		
		other();
		
		counterBuilderExample(registry);
		
		System.out.printf("\nTimer...");
		
		Timer timer = registry.timer("execution.timer");
		timer.record(() -> {
			for (int i = 0; i < 100; i++) {
				System.out.printf(" " + i);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
		});
		
		System.out.printf("\nTiempo invertido: %f", timer.totalTime(TimeUnit.MILLISECONDS));
		
		List<String> list = new ArrayList<>(4);
		Gauge gauge = Gauge.builder("list.size", list, List::size).register(registry);
		System.out.printf("\nTamaño de la lista %f", gauge.value());
		
		list.addAll(Arrays.asList("a", "b", "c", "d", "e", "f"));
		System.out.printf("\nTamaño de la lista %f", gauge.value());
	}

	private static void counterBuilderExample(MeterRegistry registry) {
		
		Counter counter = Counter
				.builder("com.gmr")
			    .description("Contador de pruebas para el builder")
			    .tags("test", "Métricas con Micrometer")
			    .register(registry);
		
		counter.increment();
		counter.increment(175);

		System.out.printf("\nValor contador con Builder %f", counter.count());
		
	}

	private static void other() {
		// Esto podría estar en otra clase
		
		CompositeMeterRegistry composite = Metrics.globalRegistry;
		
		Counter counter = composite.counter("numero.empleados", "oficina", "Pepito Perez");
		counter.increment(150);
		
		// Imprimirá 351 porque continua el Counter creado en main
		System.out.printf("Número de empleados %f", counter.count());
	}
}
