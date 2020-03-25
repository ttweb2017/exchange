package com.voicetm.karaoke.config;

import com.voicetm.karaoke.domain.User;
import com.voicetm.karaoke.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final UserService userSevice;

    private final PasswordEncoder passwordEncoder;

    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public WebSecurityConfig(
            JwtRequestFilter jwtRequestFilter,
            PasswordEncoder passwordEncoder,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            UserService userSevice) {

        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userSevice = userSevice;
    }

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers(
                            "/",
                            "/static/**",
                            "/img/**",
                            "/api/singers",
                            "/api/singers/*",
                            "/api/categories",
                            "/api/categories/*",
                            "/api/videos",
                            "/api/songs",
                            "/api/songs/top/*",
                            "/api/songs/update-watch-count/*",
                            "/api/top5-videos",
                            "/api/top10-videos",
                            "/api/top20-videos",
                            "/api/update-watch-count/*",
                            "/api/full-video/**",
                            "/api/video/**",
                            "/api/play-song/**",
                            "/api/auth/register",
                            "/api/auth/activate/**",
                            "/api/auth/authenticate")
                    .permitAll()
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors();

        // Add a filter to validate the tokens with every request
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSevice)
                .passwordEncoder(passwordEncoder);
    }
}
