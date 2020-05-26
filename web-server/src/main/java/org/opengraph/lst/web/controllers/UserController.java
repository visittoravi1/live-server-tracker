package org.opengraph.lst.web.controllers;

import org.opengraph.lst.core.beans.User;
import org.opengraph.lst.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends ControllerHelper {
	
	@Autowired
	private UserService userService;

	@GetMapping("/me")
	public User profile() {
		return userService.get(this.getUserId());
	}
}
