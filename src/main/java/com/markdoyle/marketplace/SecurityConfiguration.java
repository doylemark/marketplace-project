package com.markdoyle.marketplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.markdoyle.marketplace.services.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Autowired
	UserDetailsService userDetailsService;

	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	@Order(0)
	SecurityFilterChain staticEndpoints(HttpSecurity http) throws Exception {
		http
				.securityMatcher("/css/**", "/js/**", "/img/**")
				.headers((headers) -> headers.cacheControl((cache) -> cache.disable()))
				.authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll());

		return http.build();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(requests) -> requests.requestMatchers("/", "/cars/**", "/car").permitAll()
						.requestMatchers("/css/**", "/img/**")
						.permitAll()
						.requestMatchers("/cart").authenticated())
				.logout((logout) -> logout.permitAll())
				.formLogin((customizer) -> customizer.permitAll().defaultSuccessUrl("/cart", true));

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
