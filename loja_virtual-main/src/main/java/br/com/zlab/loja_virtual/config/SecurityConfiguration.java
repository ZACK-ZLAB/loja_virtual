package br.com.zlab.loja_virtual.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.zlab.loja_virtual.exception.handler.AcessoNaoAutorizadoHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
    		"/api/v1/auth/**",
    		"/index",
    		"/notificacaoapiasaas",
    		"/requisicaojunoboleto/**",
    		"/pagamento/**",
    		"/resources/**",
    		"/static/**",
    		"/templates/**",
    		"classpath:/static/**",
    		"classpath:/resources/**",
    		"classpath:/templates/**","/**"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    
    @Autowired
	private AcessoNaoAutorizadoHandler acessoNaoAutorizadoHandler;
    
    
	@Bean
	public UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource() {
	    CorsConfiguration corsConfiguration = new CorsConfiguration();
	    corsConfiguration.applyPermitDefaultValues(); 
	    corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
	    corsConfiguration.setAllowedMethods(Arrays.asList("*"));
	    corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
	    corsConfiguration.setExposedHeaders(Arrays.asList("*"));
	    UrlBasedCorsConfigurationSource ccs = new UrlBasedCorsConfigurationSource();
	    ccs.registerCorsConfiguration("/**", corsConfiguration);
	    return ccs;
	}
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(c -> urlBasedCorsConfigurationSource())
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
                .exceptionHandling((ex) -> {
    				ex.accessDeniedHandler(acessoNaoAutorizadoHandler);				
    			})
        ;

        return http.build();
    }
}
