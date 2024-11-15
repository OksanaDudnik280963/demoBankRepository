package com.example.demoBankApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import static com.example.demoBankApplication.global.Constants.PASS;
import static com.example.demoBankApplication.global.Constants.USER;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {
/*
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername(USER)
                .password(passwordEncoder.encode(PASS))
               // .roles("USER")
                .build();

        UserDetails admin = User.withUsername(USER)
                .password(passwordEncoder.encode(PASS))
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf
                            .ignoringRequestMatchers( "/api/**")
                            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                            .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
                    .authorizeHttpRequests(auth -> {
                        auth.anyRequest().permitAll();//.authenticated();
                    })
                    .httpBasic(Customizer.withDefaults());
//Using generated security password: e8038873-90b9-4328-829b-5bc8e8f3ce94
        return http.build();
    }


        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser(USER).password(passwordEncoder().encode(PASS)).roles("USER", "ADMIN");
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
}
