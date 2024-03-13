package pl.korpetycje.demo.configuration;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import pl.korpetycje.demo.model.Role;
import pl.korpetycje.demo.model.User;
import pl.korpetycje.demo.repository.UserRepository;
import pl.korpetycje.demo.service.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.GET, "/").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/login").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/register").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/register").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/log_out").permitAll();
                    auth.requestMatchers("/users").permitAll();
                    auth.requestMatchers("/add_lesson").hasAuthority("ADMIN");
                    auth.requestMatchers("/add_exercise").hasAuthority("ADMIN");
                    auth.requestMatchers("/exercises/**").hasAuthority("ADMIN");
                    auth.requestMatchers("/exercise/**").hasAuthority("ADMIN");
                    auth.requestMatchers("/send/**").hasAuthority("ADMIN");
                    auth.requestMatchers("/send_form").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/log_out").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/matpodst").hasAnyAuthority("ADMIN", "USER");
                    auth.requestMatchers(HttpMethod.GET, "/matpodst/*").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/matpodst/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/matpodst/**").hasAuthority("ADMIN");
                    auth.anyRequest().authenticated();
                })

                .logout(LogoutConfigurer::permitAll)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
        return (web) -> web.ignoring().requestMatchers("/css/**");
    }

}
