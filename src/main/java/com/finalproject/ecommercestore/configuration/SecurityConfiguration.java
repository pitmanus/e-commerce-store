package com.finalproject.ecommercestore.configuration;

import com.finalproject.ecommercestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
   public BCryptPasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
   }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //.antMatchers("/**")
                .antMatchers(
                        "/registration**",
                        "/my-account",
                        "/js/**",
                        "/css/**",
                        "/img/**"
                ).permitAll().anyRequest().authenticated()
                .and()
                .csrf().disable().cors().disable()
                .formLogin().failureUrl("/login?error").defaultSuccessUrl("/")
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();



    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
auth.authenticationProvider(authenticationProvider());
auth.inMemoryAuthentication()
        .withUser("admin")
        .password(passwordEncoder().encode("test"))
        .roles("ADMIN");
    }
}
