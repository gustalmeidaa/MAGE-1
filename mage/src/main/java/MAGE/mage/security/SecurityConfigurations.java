package MAGE.mage.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
// 💡 Importe o CorsConfigurationSource
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// 💡 Importe o CorsConfigurationSource
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    // 💡 INJETAR O BEAN DE CONFIGURAÇÃO CORS (Se definido em outra classe)
    @Autowired(required = false) // Use required = false se o bean for opcional ou definido dinamicamente
    private CorsConfigurationSource corsConfigurationSource;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // 💡 PASSO CRUCIAL: Habilita e configura o CORS
                .cors(cors -> {
                    if (corsConfigurationSource != null) {
                        cors.configurationSource(corsConfigurationSource);
                    } else {
                        cors.disable();
                    }
                })

                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/maquinas/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/maquinas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/maquinas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/administradores").authenticated()
                        .requestMatchers(HttpMethod.POST, "/administradores").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // ... os outros Beans permanecem inalterados ...

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}