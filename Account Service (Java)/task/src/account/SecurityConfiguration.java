package account;

import account.data.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint()) // Handles auth error
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .authorizeRequests() // manage access
                .requestMatchers(HttpMethod.POST, "api/auth/signup").permitAll()
                .requestMatchers("/actuator/shutdown").permitAll() // needs to run test
                .requestMatchers(HttpMethod.POST, "api/auth/changepass").permitAll()
                .requestMatchers(HttpMethod.GET, "api/empl/payment").permitAll()
                .requestMatchers(HttpMethod.POST, "api/acct/payments").hasRole(User.Role.ACCOUNTANT.name())
                .requestMatchers(HttpMethod.PUT, "api/acct/payments").hasRole(User.Role.ACCOUNTANT.name())
                .requestMatchers(HttpMethod.PUT, "/api/auth/access/**").hasRole(User.Role.ADMINISTRATOR.name())
                .requestMatchers(HttpMethod.GET, "api/admin/user").hasRole(User.Role.ADMINISTRATOR.name())
                .requestMatchers(HttpMethod.DELETE, "api/admin/user").hasRole(User.Role.ADMINISTRATOR.name())
                .requestMatchers(HttpMethod.PUT, "api/admin/user/role").hasRole(User.Role.ADMINISTRATOR.name())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // no session
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
