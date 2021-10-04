package com.gmr.metrics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.MeterRegistry;

@RestController
@RequestMapping("/gmenar/metrics")
public class TestMetrics {
	
	@Autowired
	private MeterRegistry registry;

	@GetMapping
	public ResponseEntity<String> get(){
		registry.counter("gmenar.metrics").increment();
		return new ResponseEntity<String>("@gregoriomena", HttpStatus.OK);
	}
}
