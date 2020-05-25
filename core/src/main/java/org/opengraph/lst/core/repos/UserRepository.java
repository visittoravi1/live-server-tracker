package org.opengraph.lst.core.repos;

import java.util.Optional;

import org.opengraph.lst.core.beans.User;

public interface UserRepository {
	Optional<User> get(String username);
}
