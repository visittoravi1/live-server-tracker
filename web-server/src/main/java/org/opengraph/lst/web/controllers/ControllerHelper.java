package org.opengraph.lst.web.controllers;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class ControllerHelper {
	public String getUserId() {
		AbstractAuthenticationToken auth = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		return auth == null ? null : (String)auth.getPrincipal();
	}
}
