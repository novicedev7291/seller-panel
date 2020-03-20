package com.seller.panel.controller;

import com.seller.panel.util.EndPointConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
	
	@GetMapping(EndPointConstants.Ping.PING)
	public ResponseEntity<Void> ping() {
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
