package com.codecool.appsystem.admin.config;

import com.codecool.appsystem.admin.config.security.JwtAuthenticationProvider;
import com.codecool.appsystem.admin.config.security.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationProvider authenticationProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {

        // match GET and POST under / for authentication.
        RequestMatcher getRequestMatcher = new AntPathRequestMatcher("/api/**", HttpMethod.GET.name(), false);
        RequestMatcher postRequestMatcher = new AntPathRequestMatcher("/api/**", HttpMethod.POST.name(), false);

        // allow /api/login
        RequestMatcher notLogin = new NegatedRequestMatcher(
                new AntPathRequestMatcher("/api/login**")
        );

        // allow /api/login/callback
        RequestMatcher notCallback = new NegatedRequestMatcher(
                new AntPathRequestMatcher("/api/login/callback")
        );

        RequestMatcher notLocation = new NegatedRequestMatcher(
                new AntPathRequestMatcher("/api/locations")
        );

        RequestMatcher orRequestMatcher = new OrRequestMatcher(getRequestMatcher, postRequestMatcher);

        RequestMatcher andRequestMatcher = new AndRequestMatcher(notCallback, notLogin, notLocation, orRequestMatcher);

        JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter(andRequestMatcher);
        authenticationTokenFilter.setAuthenticationManager(authenticationManager());
        authenticationTokenFilter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler());
        return authenticationTokenFilter;
    }

    @Bean
    public AuthenticationEntryPoint jwtAuthenticationEntryPoint(){
        return (httpServletRequest, httpServletResponse, e) ->
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Bean
    public AuthenticationSuccessHandler jwtAuthenticationSuccessHandler(){
        return (httpServletRequest, httpServletResponse, authentication) -> {
            // don't do anything.
        };
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**/*.{js,html}");
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity

                .logout().logoutUrl("/logout")

                .and()

                // we don't need CSRF because of JWT tokens
                .csrf().disable()

                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint())

        ;

        // Custom JWT based security filter
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);


    }


}
