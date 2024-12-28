package com.frapee.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {

	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withDefaultPasswordEncoder().username("bla").password("password").build());
		manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
        manager.createUser(User.withDefaultPasswordEncoder().username("admin").password("password").roles("ADMIN").build());
		return manager;
	}

	@Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }	

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
			.csrf((csrf) -> csrf
					.csrfTokenRepository(csrfTokenRepository())
					.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))		
            .authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/api/**")
				.hasRole("USER")
				.requestMatchers("/admin/**")
				.hasRole("ADMIN")
				.requestMatchers("/v3/**","/swagger-ui/**")
				.authenticated()
                .requestMatchers("/public/**")
				.permitAll()
			)

			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults())
            .build();            
    }

}
