package net.engineeringdigest.journalApp.config;

import net.engineeringdigest.journalApp.services.CustomUserDetailsServiceImplementation;
import net.engineeringdigest.journalApp.types.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsServiceImplementation customUserDetailsServiceImplementation;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        This configure is for the url endpoints
        http.authorizeRequests()
                .antMatchers(
                        "/journal/**",
                        "/user/**"
                ).authenticated()
                .antMatchers("/admin/**").hasRole(UserRoles.ADMIN.toString())
                .anyRequest().permitAll()
                .and()
                .httpBasic();

//        http.csrf().disable(); // By default, in spring boot cross site request forgery protection is enabled, due to which spring security will expect us to provide a token in the request. If this isn't disabled, the requests will return a 403 forbidden. Since now, we're going to just make server-to-server call we can safely disable it.

        // disabling default cookies management also at the same time.
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsServiceImplementation)
                .passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
