package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tacos.data.UserRepository;
import tacos.model.User;

@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)  // deprecated
@EnableMethodSecurity // for using @PreAuthorize (Spring Security 6+)
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        List<UserDetails> usersList = new ArrayList<>();
//        usersList.add(
//                new User("buzz", encoder.encode("password"),
//                        Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
//        usersList.add(
//                new User("woody", encoder.encode("password"),
//                        Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
//        return new InMemoryUserDetailsManager(usersList);
//    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null) return user;

            throw new UsernameNotFoundException("User " + username + " not found");
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/design", "/orders").hasRole("USER")
                        .requestMatchers("/", "/**").permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
//                                .loginProcessingUrl("/authenticate")
//                                .usernameParameter("user")
//                                .passwordParameter("pwd")
                        .defaultSuccessUrl("/design", true)
                )
                .oauth2Login(form -> form.loginPage("/login"))
                .logout(logout -> logout.logoutSuccessUrl("/"))
//                .csrf(AbstractHttpConfigurer::disable)    // Disable CSRF protection (not recommended)
                .build();
    }
}
