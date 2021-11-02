package com.example.spsecamigouescode.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;

import static com.example.spsecamigouescode.Security.ApplicationUserPermissions.*;
import static com.example.spsecamigouescode.Security.ApplicationUserRoles.*;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .authorizeRequests()
                .antMatchers("/", "index", "index", "css/*", "js/*").permitAll()
//                .antMatchers("/student/**", "/student", "student").hasRole(STUDENT.name())
//                .antMatchers(HttpMethod.DELETE,"/manage/**","manage/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST,"/manage/**","manage/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"/manage/**","manage/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET,"/manage/**","manage/**").hasAnyRole(ADMINTRAINEE.name(),ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
//                .httpBasic();
                .formLogin();

    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails annaSmithUser;
        annaSmithUser = User.builder().username("anna").password(
                passwordEncoder.encode("123")
        )
//                .roles(STUDENT.name())
                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        UserDetails lindaAdmin = User.builder().username("linda").password(
                passwordEncoder.encode("123")
        )
//                .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails adminTraineeTom = User.builder().username("tom").password(
                passwordEncoder.encode("123")
        )
//                .roles(ADMINTRAINEE.name())
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                annaSmithUser, lindaAdmin, adminTraineeTom
        );

    }
}
