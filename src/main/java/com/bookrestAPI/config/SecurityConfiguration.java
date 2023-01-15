package com.bookrestAPI.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //--> use to enable @PreAuthorize("hasRole("ADMIN")")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	// For database Authentication
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // use to disable csrf which is cross site request forgery, if this is enable
							  // we cannot perform post and put methods on non-browser client
			.authorizeRequests()
			.antMatchers(HttpMethod.GET).hasAnyRole("ADMIN","REGULAR")
			.antMatchers("/**").hasRole("ADMIN")
			.anyRequest()
			.authenticated()
			.and()
	        .httpBasic(); //use for basic authentication
			//.formLogin();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
//		For inMemoryAuthentication
//		auth.inMemoryAuthentication().withUser("Shivam").password(this.passwordEncoder().encode("12345"))
//				.roles("ADMIN");
//		auth.inMemoryAuthentication().withUser("Ayush").password(this.passwordEncoder().encode("67890"))
//				.roles("REGULAR");
		
//		For database Authentication
		auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}

//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfiguration {
//
//	@Autowired
//	private CustomUserDetailService customUserDetailService;
//
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(customUserDetailService);
//		authProvider.setPasswordEncoder(passwordEncoder());
//		return authProvider;
//	}
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.csrf().disable() // use to disable csrf which is cross site request forgery, if this is enable
//								// we cannot perform post and put methods on non-browser client
//				.authorizeRequests()
//				.antMatchers(HttpMethod.GET).hasAnyRole("ADMIN","REGULAR")
//				.antMatchers("/**").hasRole("ADMIN")
//				.anyRequest().authenticated().and().httpBasic(); // use for basic authentication
//		// .formLogin();
//		http.authenticationProvider(authenticationProvider());
//		return http.build();
//	}
//
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder(10);
//	}
//}
