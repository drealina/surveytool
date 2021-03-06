package de.hsba.bi.Umfrage;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().sameOrigin().and() // allow the h2-console to be used in a frame
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/surveys/**").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/h2-console/**").permitAll() // enable access to the h2-console
                .antMatchers("/js/**").permitAll() // permit JS resources
                .antMatchers("/img/**").permitAll() // permit IMG resources
                .antMatchers("/css/**").permitAll() // permit CSS resources
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/login/")
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
