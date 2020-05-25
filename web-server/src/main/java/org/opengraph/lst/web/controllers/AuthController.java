package org.opengraph.lst.web.controllers;

import java.util.Map;

import org.opengraph.lst.core.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private UserService userService;
	
	public AuthController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/login")
	public Map<String, String> login(@RequestBody Map<String, String> credentials) {
		return Map.of("token", userService.login(credentials.get("username"), credentials.get("password")));
	}
}
