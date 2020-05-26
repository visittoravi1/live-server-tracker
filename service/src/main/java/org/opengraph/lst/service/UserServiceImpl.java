package org.opengraph.lst.service;

import org.opengraph.lst.core.beans.User;
import org.opengraph.lst.core.exceptions.ResourceNotFoundException;
import org.opengraph.lst.core.repos.UserRepository;
import org.opengraph.lst.core.service.UserService;
import org.opengraph.lst.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository repository;
	private final PasswordEncoder encoder;
	private final JwtUtil jwtUtil;
	
	@Autowired
	public UserServiceImpl(UserRepository repository, PasswordEncoder encoder, JwtUtil jwtUtil) {
		this.repository = repository;
		this.encoder = encoder;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public String login(String username, String password) {
		return repository.get(username)
				.filter(user -> this.encoder.matches(password, user.getPassword()))
				.map(jwtUtil::generateToken)
				.orElseThrow(() -> new BadCredentialsException("Wrong Credentials"));
	}

	@Override
	public User get(String userName) {
		return repository.get(userName).orElseThrow(() -> new ResourceNotFoundException(User.class.getSimpleName(), userName));
	}

}
