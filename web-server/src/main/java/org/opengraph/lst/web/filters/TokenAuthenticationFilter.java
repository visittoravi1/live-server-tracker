package org.opengraph.lst.web.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.opengraph.lst.core.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationFilter.class);
	public static final String JWT_HEADER = "X-AUTH";
	
	private JwtUtil jwtUtil;

	public TokenAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher, JwtUtil jwtUtil) {
		super(requiresAuthenticationRequestMatcher);
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		if (authentication == null) {
			throw new InsufficientAuthenticationException("UNAUTHORIZED");
		}
		return authentication;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(authentication);
		SecurityContextHolder.setContext(securityContext);
		chain.doFilter(request, response);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		failed.setStackTrace(new StackTraceElement[] {});
		response.setContentType("applicaiton/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		PrintWriter writer = response.getWriter();
		writer.write(new ObjectMapper().writeValueAsString(failed));
		writer.flush();
		writer.close();
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(JWT_HEADER);
		if (StringUtils.isNotEmpty(token)) {
			try {
				Claims claims = jwtUtil.getClaims(token);
				return new UsernamePasswordAuthenticationToken(claims.getId(),
						claims.getSubject(),
						Arrays.stream(claims.getSubject().split(",")).filter(StringUtils::isNotEmpty)
								.map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
			} catch (JwtException | IllegalArgumentException ex) {
				LOGGER.error("Invalid token {}", token, ex);
				throw new SessionAuthenticationException("Session is not valid");
			}
		}else {
			throw new AuthenticationCredentialsNotFoundException("Please login to continue");
		}
	}

}
