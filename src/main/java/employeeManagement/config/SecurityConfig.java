package employeeManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // Login page
                        .requestMatchers("/", "/adminlogin").permitAll()

                        // Static resources
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/pictures/**",
                                "/webjars/**"
                        ).permitAll()

                        // ADMIN only
                        .requestMatchers(
                                "/employees",
                                "/employees/new",
                                "/employees/save",
                                "/employees/update/**",
                                "/employees/Setleave",
                                "/employees/leave/save",
                                "/leaveDetails/save",
                                "/leaveDetails/view",
                                "/leaveDetails/**",
                                "/employees/leave/allleaves",
                                "/employees/leave/allleaves/**"
                        ).hasRole("ADMIN")

                        // EMPLOYEE + ADMIN
                        .requestMatchers(
                                "/employees/empprofile/view/**",
                                "/employees/leave/view/**",
                                "/employees/leave/applyNew/**",
                                "/employees/apply/save",
                                "/employees/leave/leaveStatus/**"
                        ).hasAnyRole("EMPLOYEE", "ADMIN")

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form

                        .loginPage("/")

                        .loginProcessingUrl("/adminlogin")

                        .defaultSuccessUrl(
                                "/login-success",
                                true)

                        .failureUrl(
                                "/?error=true")

                        .permitAll()
                )

                .logout(logout -> logout

                        .logoutUrl("/adminlogout")

                        .logoutSuccessUrl("/")

                        .invalidateHttpSession(true)

                        .deleteCookies("JSESSIONID")

                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}