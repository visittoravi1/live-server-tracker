package org.opengraph.lst.core.util;

import java.util.Date;

import org.opengraph.lst.core.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	private static final String ISSUER = "http://www.opengraph.co.in";
	
	@Autowired
	private Environment env;

	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(env.getProperty("jjwt.secret").getBytes())
				.parseClaimsJws(token).getBody();
	}

	public String generateToken(User user) {
		return Jwts.builder()
				.setSubject("ROLE_" + user.getRole())
				.setId(user.getUserName())
				.compressWith(CompressionCodecs.DEFLATE)
				.setIssuer(ISSUER)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()
						+ Long.parseLong(env.getProperty("jjwt.time.expiry.hours")) * 60 * 60 * 1000))
				.signWith(SignatureAlgorithm.HS256, env.getProperty("jjwt.secret").getBytes()).compact();
	}

}
