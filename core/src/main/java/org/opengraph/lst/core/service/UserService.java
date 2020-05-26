package org.opengraph.lst.core.service;

import org.opengraph.lst.core.beans.User;

public interface UserService {
	
	/**
	 * Login a user, if given credentials found then return token
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	String login(String username, String password);
	
	/**
	 * Get user by username
	 * 
	 * @param userName
	 * @return
	 */
	User get(String userName);
}
