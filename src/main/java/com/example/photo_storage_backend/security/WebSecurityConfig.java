package com.example.photo_storage_backend.security;

import com.example.photo_storage_backend.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.photo_storage_backend.service.UserDetailsServiceImpl;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;
    private AppUserRepository appUserRepository;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AppUserRepository appUserRepository) {
        this.userDetailsService = userDetailsService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/upload").hasAnyRole("USER","ADMIN")
                .antMatchers("/uploadForm").hasAnyRole("USER","ADMIN")
                .antMatchers("/gallery").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .csrf().disable();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


//    @EventListener(ApplicationReadyEvent.class)
//    public void start(){
//        AppUser user =new AppUser("user",passwordEncoder().encode("user"),"ROLE_USER");
//        AppUser admin =new AppUser("admin", passwordEncoder().encode("admin"), "ROLE_ADMIN");
//        appUserRepository.save(user);
//        appUserRepository.save(admin);
//    }
}
