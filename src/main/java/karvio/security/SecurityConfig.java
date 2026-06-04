package karvio.security;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class SecurityConfig {

    private final GatewayDocumentFilter gatewayDocumentFilter;

    public SecurityConfig(GatewayDocumentFilter gatewayDocumentFilter) {
        this.gatewayDocumentFilter = gatewayDocumentFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(doc -> doc
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(gatewayDocumentFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public RequestInterceptor feignHeaderPropagationInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes)
                    RequestContextHolder.getRequestAttributes();

            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();

                String username = request.getHeader("X-User-Name");
                String roles = request.getHeader("X-User-Roles");

                if (username != null) {
                    requestTemplate.header("X-User-Name", username);
                }
                if (roles != null) {
                    requestTemplate.header("X-User-Roles", roles);
                }
            }
        };
    }
}

