package com.gmr.metrics.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@RestController
@RequestMapping("/gmenar/metrics")
public class TestMetrics {
	
	
	private static final Logger log = LoggerFactory.getLogger(TestMetrics.class);

	
	@Autowired
	private MeterRegistry registry;

	@GetMapping
	public ResponseEntity<String> get(){
		registry.counter("gmenar.metrics").increment();
		return new ResponseEntity<String>("@gregoriomena", HttpStatus.OK);
	}
	
	@GetMapping("/timer")
	public ResponseEntity<String> timer(){
		Timer timer = registry.timer("gmenar.timer");
		timer.record(() -> {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				log.error("Timer sleep error", e);
			}
			
			log.info("Usless task");
		});
		
		return new ResponseEntity<String>("@gregoriomena", HttpStatus.OK);
	}
	
	@GetMapping("/timermetodo")
	@Timed("gmenar.timer.metodo")
	public ResponseEntity<String> timerMetodo(){
		log.info("Medirá el tiempo de todo el método, no sólo del trozo de código que nos interese");
		return new ResponseEntity<String>("@gregoriomena", HttpStatus.OK);
	}
}
