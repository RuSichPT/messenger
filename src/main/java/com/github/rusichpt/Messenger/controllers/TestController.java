package com.github.rusichpt.Messenger.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {

	@GetMapping("/all")
	public String allAccess() {
		return "public API";
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public String userAccess() {
		return "user API";
	}

}
