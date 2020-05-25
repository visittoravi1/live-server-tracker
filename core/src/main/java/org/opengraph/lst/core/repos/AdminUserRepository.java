package org.opengraph.lst.core.repos;

import java.util.Optional;

import org.opengraph.lst.core.beans.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class AdminUserRepository implements UserRepository {
	
	private static final String ADMIN = "admin";
	
	private PasswordEncoder encoder;
	
	public AdminUserRepository(PasswordEncoder encoder) {
		this.encoder = encoder;
	}

	@Override
	public Optional<User> get(String username) {
		if (ADMIN.equals(username)) {
			User user = new User();
			user.setUserName(ADMIN);
			user.setPassword(encoder.encode(ADMIN));
			user.setRole(ADMIN);
			return Optional.of(user);
		}
		return Optional.empty();
	}

}
