
package br.com.zlab.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable());
		return http.authorizeHttpRequests(
				authorizeConfig ->{
					
					authorizeConfig.requestMatchers("/salvarAcesso").permitAll();
					authorizeConfig.requestMatchers("/deleteAcesso").permitAll();
					authorizeConfig.requestMatchers("/deleteAcessoPorId/").permitAll();
					authorizeConfig.anyRequest().authenticated();
					
				})
			.formLogin(Customizer.withDefaults())
			.build();
	}
}

//http.csrf((csrf) -> csrf.disable());
//return http.build();
// "/salvarAcesso", "/deleteAcesso"