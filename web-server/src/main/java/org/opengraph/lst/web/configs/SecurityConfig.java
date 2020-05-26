package org.opengraph.lst.web.configs;

import java.util.ArrayList;
import java.util.List;

import org.opengraph.lst.core.util.JwtUtil;
import org.opengraph.lst.web.filters.TokenAuthenticationFilter;
import org.opengraph.lst.web.handlers.AuthorizationFailureHandler;
import org.opengraph.lst.web.handlers.RestAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.authentication.AuthenticationManagerBeanDefinitionParser;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
		.authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS).permitAll()
		.antMatchers("/user/**").hasAnyRole("admin")
		.anyRequest().permitAll()
		.and()
		.exceptionHandling()
		.accessDeniedHandler(new RestAccessDeniedHandler())
		.authenticationEntryPoint(entryPoint());
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new AuthenticationManagerBeanDefinitionParser.NullAuthenticationProvider());
	}
	
	private TokenAuthenticationFilter authenticationFilter( ) throws Exception {
		NegatedRequestMatcher notOptionsMatcher = new NegatedRequestMatcher(
				new AntPathRequestMatcher("/**", "OPTIONS"));
		AntPathRequestMatcher authMatcher = new AntPathRequestMatcher("/user/**");
		List<RequestMatcher> matchers = new ArrayList<>();
		matchers.add(authMatcher);
		matchers.add(notOptionsMatcher);
		AndRequestMatcher andRequestMatcher = new AndRequestMatcher(matchers);
		TokenAuthenticationFilter filter = new TokenAuthenticationFilter(andRequestMatcher, jwtUtil);
		filter.setAuthenticationManager(this.authenticationManager());
		return filter;
	}
	
	@Bean
	public AuthenticationEntryPoint entryPoint() {
		return new AuthorizationFailureHandler();
	}

}
