package com.kartik.spring_security_demo.config;

import com.kartik.spring_security_demo.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//when you send  a request there is a way to spring security verifies the credentials ,it uses something called username password authentication and we dont want to use  that and want our own technique
//    whenever we use spring framework for the web ,behind the scenes its all the servelet
//    even if you are using spring framework behind the scenes its getting converted into servelets
//    servelets is responsibklr for accepting the user request and respond it with some data
//     and we have inbuilt filters and need to pass jwt filter
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationProvider authProvider(){

        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;

    }

//by deafult spring will create this object for you

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disables CSRF protection
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/register", "/login").permitAll()  // Allow access to these URLs without authentication
                        .anyRequest().authenticated()  // All other requests need to be authenticated
                )
                  // Enables HTTP Basic authentication
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Stateless session
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  // Add JWT filter

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails user= User.withDefaultPasswordEncoder()
                .username("kart")
                .password("123")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
   public  AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
