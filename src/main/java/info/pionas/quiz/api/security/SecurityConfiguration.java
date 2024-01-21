package info.pionas.quiz.api.security;

import info.pionas.quiz.api.ApiConfiguration;
import info.pionas.quiz.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
class SecurityConfiguration {


    private final UserDetailsFactory userDetailsFactory;

    @Bean
    public UserDetailsService userDetailsService(AuthenticationManagerBuilder auth, UserRepository userRepository) {
        return username -> userRepository.get(username)
                .map(userDetailsFactory::create)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not exist", username)));
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration(ApiConfiguration.API_CONTEXT + "/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AccessTokenPreAuthorizationFilter accessTokenPreAuthorizationFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeExchangeSpec ->
                        authorizeExchangeSpec.requestMatchers(HttpMethod.OPTIONS).permitAll()
                                .requestMatchers(ApiConfiguration.API_CONTEXT + "/login").permitAll()
                                .requestMatchers(ApiConfiguration.API_CONTEXT + "/register").permitAll()
                                .requestMatchers(ApiConfiguration.API_CONTEXT + "/**").authenticated()
                                .requestMatchers("/**").permitAll()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
                    httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(getAuthenticationEntryPoint());
                    httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(getAccessDeniedHandler());
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(accessTokenPreAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    private AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return (request, response, authException) -> response.sendError(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
    }

    private AccessDeniedHandler getAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> response.sendError(HttpStatus.FORBIDDEN.value(), accessDeniedException.getMessage());
    }
}
