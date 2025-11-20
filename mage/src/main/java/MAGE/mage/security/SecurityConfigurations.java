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
                        // Se o CorsConfigurationSource não for injetado, você pode usar um default
                        // ou deixar para o Spring Security tentar encontrar a configuração padrão.
                        cors.disable(); // Desativa o cors aqui se não puder configurar, mas isso não resolverá o problema.
                    }
                })
                // Se você não tiver o bean injetado, uma solução simples é:
                // .cors(Customizer.withDefaults())

                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
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