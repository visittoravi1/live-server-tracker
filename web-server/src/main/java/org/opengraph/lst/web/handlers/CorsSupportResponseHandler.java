package org.opengraph.lst.web.handlers;

import javax.servlet.http.HttpServletResponse;

public class CorsSupportResponseHandler {
	
	public void modifyResponse(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		response.addHeader("Access-Control-Allow-Headers",
				"Content-Type, Access-Control-Request-Headers, Accept, Accept-Encoding, Accept-Language, Access-Control-Request-Method, Connection, Host, Origin, Referer, User-Agent, X-XSRF-TOKEN, X-Auth-Token, X-Requested-With,lang, filename");
		response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, filename");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Max-Age", "3600");
	}

}
