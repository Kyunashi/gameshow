package com.kyunashi.gameshow.config;

import com.kyunashi.gameshow.service.JpaUserDetailsService;
import jakarta.servlet.DispatcherType;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Arrays;

/**
 * Class to configure spring security rules which spring then inforces
 * and provide utility like password encryption and user authentication aswell as logout so that spring can en
 */
@Configuration
@EnableWebSecurity
@EnableWebSocketMessageBroker
@CommonsLog
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfig implements WebSocketMessageBrokerConfigurer {

    private final JpaUserDetailsService userDetailsService;


    /**
     *
     * @param userDetailsService
     * @param passwordEncoder
     * @return manager to use for encoding of passwords and authentication of users
     */
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);

        return providerManager;
    }

    /**
     * stores currently authenticated users using sessions
     * @return repository containing these sessions / logins
     */
    @Bean
    public SecurityContextRepository securityContextRepository(){
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository());
    }


    /**
     * spring configuration that enables cors and permits certains domains/ips to connect
     * also configures methods, allowed headers and credential allowance
     * @return configuration which is used in the security filter chain
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://192.168.178.42:[*]", "http://localhost:[*]", "http://192.168.178.24:[*]", "192.168.178.24:[*]"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * Filter chain enforces security rules
     * these rules are configured with this method
     * inclues checking for authentication if needed, AUthorization if needed and enabling openly accessible entpoints
     * deleting Cookies and the session once logged out
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .userDetailsService(userDetailsService)
                .securityContext((securityContext) -> securityContext
                        .requireExplicitSave(true)
                        .securityContextRepository(securityContextRepository()))
                .authorizeHttpRequests((authorize) -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/room/create").permitAll()
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/api/room/join/[a-zA-Z0-9]{10}")).permitAll()
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/api/room/delete/[a-zA-Z0-9]{10}")).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/all").hasRole("ADMIN")
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/api/users/current")).hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/users/update").hasRole("USER")
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/api/users/\\d{1,10}")).hasRole("USER")
                        .anyRequest().authenticated())
                        .logout((logout) -> logout
                                .logoutUrl("/api/auth/logout")
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                        );

        return http.build();
    }


    /**
     * password encoder can be used to encode user passwords on registration to store them in the database encrypted
     * @return passwordencoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/portfolio");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/room");
    }

}
