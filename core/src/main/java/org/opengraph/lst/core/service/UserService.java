package org.opengraph.lst.core.service;

public interface UserService {
	
	/**
	 * Login a user, if given credentials found then return token
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	String login(String username, String password);
}
