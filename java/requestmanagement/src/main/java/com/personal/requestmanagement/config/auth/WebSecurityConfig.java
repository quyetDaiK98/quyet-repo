package com.personal.requestmanagement.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	};
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		
		auth.inMemoryAuthentication()
        .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
	}
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
	    http
	      .csrf().disable()
	      .authorizeRequests()
	      .antMatchers("/admin/**").hasRole("ADMIN")
	      .antMatchers("/login*").permitAll()
	      .anyRequest().authenticated()
	      .and()
	      .formLogin()
	      .loginPage("/login")
//	      .loginProcessingUrl("/perform_login")
	      .defaultSuccessUrl("/index", true)
	      .failureUrl("/login?error=true")
//	      .failureHandler(authenticationFailureHandler())
	      .and()
	      .logout()
	      .logoutUrl("/perform_logout")
	      .deleteCookies("JSESSIONID");
//	      .logoutSuccessHandler(logoutSuccessHandler());
	      // ...
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring()
	    	.antMatchers("/dist/**")
	    	.antMatchers("/plugins/**");
	}

}
