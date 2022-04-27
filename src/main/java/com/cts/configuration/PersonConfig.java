package com.cts.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cts.filter.JwtExceptionHandlerFilter;
import com.cts.filter.JwtRequestFilter;
import com.cts.service.impl.AppUserProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableCaching
public class PersonConfig extends WebSecurityConfigurerAdapter {

	/*
	 * @Autowired private AppUserProvider appUserProvider;
	 */

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	private JwtExceptionHandlerFilter exceptionHandlerFilter;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/static/**", "/h2-console/**");
	}

	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("users");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable()
				.cors()
				.and()
				.authorizeRequests()
				.antMatchers("/", "/static/**", "/authenticate/**", "/people/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.logout()
				.logoutUrl("/logout")
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.authenticationProvider(appUserProvider); }
	 * 
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * http.authorizeRequests() .antMatchers("/authenticate") .permitAll()
	 * .anyRequest() .authenticated() .and() .httpBasic(); }
	 */

}
