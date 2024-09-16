package com.example.demo.config;

import com.example.demo.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final AuthTokenFilter authTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authRequest -> authRequest

                        .requestMatchers("/api/auth/**").permitAll() // Registro, login, etc.
                        .requestMatchers("/api/v1/**").authenticated()
                        .requestMatchers("/api/auth/register").permitAll()

                        // Endpoints accesibles solo por admins
                        .requestMatchers("/api/v1/users/all").hasAuthority("ADMIN") // Ver todos los usuarios
                        .requestMatchers("/api/v1/courses/create").hasAuthority("ADMIN") // Crear curso
                        .requestMatchers("/api/v1/courses/update/{id}").hasAuthority("ADMIN") // Actualizar curso
                        .requestMatchers("/api/v1/courses/delete/{id}").hasAuthority("ADMIN") // Eliminar curso
                        .requestMatchers("/api/v1/shoppingCart/update/{id}").hasAuthority("ADMIN") // Actualizar carrito (si necesario para admin)
                        .requestMatchers("/api/v1/shoppingCart/delete/{id}").hasAuthority("ADMIN") // Eliminar carrito (si necesario para admin)

                        // Endpoints accesibles por usuarios y admins
                        .requestMatchers("/api/v1/users/update/{id}").hasAnyAuthority("ADMIN", "USER") // Actualizar perfil (propio para usuario, todos para admin)
                        .requestMatchers("/api/v1/users/delete/{id}").hasAnyAuthority("ADMIN", "USER") // Eliminar perfil (propio para usuario, todos para admin)
                        .requestMatchers("/api/v1/shoppingCart/{cartId}/courses/{courseId}").hasAnyAuthority("ADMIN", "USER") // Añadir o eliminar curso del carrito (propio)

                        // Endpoints accesibles por usuarios autenticados
                        .requestMatchers("/api/v1/users/user/{id}").authenticated() // Ver perfil propio
                        .requestMatchers("/api/v1/experiences/create").authenticated() // Crear experiencia laboral (propia)
                        .requestMatchers("/api/v1/experiences/experience/{id}").authenticated() // Ver experiencia laboral (propia)
                        .requestMatchers("/api/v1/shoppingCart/create").authenticated() // Crear carrito
                        .requestMatchers("/api/v1/shoppingCart/cart/{id}").authenticated() // Ver carrito (propio)
                        .requestMatchers("/api/v1/payments").authenticated() // Realizar pagos

                        // Endpoints que pueden ser accedidos por cualquier usuario sin autenticación
                        .requestMatchers("/api/v1/courses/all").permitAll() // Cursos disponibles para todos
                        .requestMatchers("/api/v1/experiences/all").permitAll() // Experiencias laborales públicas si es necesario
                        .requestMatchers("/api/v1/users/create").permitAll() // Registro de usuario
                        .requestMatchers("/api/v1/courses/course/{id}").hasAnyAuthority("ADMIN", "USER") // Ver detalle del curso (admin y usuario)
                        .requestMatchers("/api/v1/experiences/update/{id}").hasAnyAuthority("ADMIN", "USER") // Actualizar experiencia laboral (admin y usuario)
                        .requestMatchers("/api/v1/experiences/delete/{id}").hasAnyAuthority("ADMIN", "USER") // Eliminar experiencia laboral (admin y usuario)

                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
