package Projects.DoItNow_BackEnd_App.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	JWTAuthFilter jwtFilter;

	@Autowired
	CorsFilter corsFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// Cross-Site Request Forgery: prevent unwanted actions without user consent
		http.csrf(c -> c.disable());

		// to have stateless sessions in line with REST API approach
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests(auth -> auth.requestMatchers("/tasks/**").authenticated());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/users/**").authenticated());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll());

		// to configure filters order
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(corsFilter, JWTAuthFilter.class);

		return http.build();
	}

	@Bean
	PasswordEncoder encoder() {
		// to set bcrypt password hashing algorithm iterations x11 times
		return new BCryptPasswordEncoder(11);
	}

}
