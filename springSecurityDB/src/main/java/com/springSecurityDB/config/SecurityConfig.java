package com.springSecurityDB.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(WebSecurity web) throws Exception	{
		web.ignoring()
			.antMatchers("/css/**","/js/**","/images/**");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
	        .authorizeRequests()
	        .antMatchers("/admin/**").hasAnyRole("ADMIN")
	        .antMatchers("/user/**").hasAnyRole("USER")
	        .antMatchers("/", "/main", "/about").permitAll()
	        .antMatchers("/**").permitAll()
	        .anyRequest().authenticated()
	        .and()
	        .formLogin()
	        	.usernameParameter("userId")
	        	.passwordParameter("password")
	        	.loginPage("/login")
	        	.failureUrl("/login")
	        	.defaultSuccessUrl("/")
	        .permitAll()
			.and()
				.logout()
				.logoutSuccessUrl("/")
				.permitAll()
			.and()
				.exceptionHandling()
				.accessDeniedPage("/access-denied");
	}
}