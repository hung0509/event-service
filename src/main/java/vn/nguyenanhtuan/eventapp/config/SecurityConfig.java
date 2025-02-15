package vn.nguyenanhtuan.eventapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig {
    private final String[] URL_PUBLIC = {
            "/auth", "/auth/login", "/auth/refresh", "/auth/logout", "/auth/introspect",
            "/users", "auth/outbound/authentication"
    };

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Autowired
    public CustomJWTDecoder customJWTDecoder;

    @Bean
    //fix
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request -> {
                    request.requestMatchers(HttpMethod.POST, URL_PUBLIC).permitAll()
                            .requestMatchers(HttpMethod.GET, "/payments/**").permitAll() //thanh toan
                            .requestMatchers(HttpMethod.GET, "/users/verify/**").permitAll()//xac thuc email
                            .anyRequest().authenticated();
                }
        );
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.oauth2ResourceServer(
                oauth2 -> oauth2.jwt(
                                jwtConfigurer -> jwtConfigurer.decoder(customJWTDecoder)// custom decode jwt
                                        .jwtAuthenticationConverter(jwtAuthenticationConverter())// custom prefix
                        )
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())// custom error 401
        );

        return httpSecurity.build();
    }

    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

}
