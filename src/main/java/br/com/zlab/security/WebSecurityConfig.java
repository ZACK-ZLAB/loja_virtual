
package br.com.zlab.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers(HttpMethod.POST, "/salvarAcesso", "/deleteAcesso").permitAll()
						.requestMatchers(HttpMethod.GET, "/salvarAcesso", "/deleteAcesso").permitAll()
				);
		// disable CSRF
		http.csrf((csrf) -> csrf.disable());
		return http.build();
	}

}