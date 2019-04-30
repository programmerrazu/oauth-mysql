package org.razu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

//****************************
    //    @Autowired
//    private JwtAuthenticationProvider authenticationProvider;
//    @Autowired
//    private JwtAuthenticationEntryPoint authenticationEntryPoint;
//    @Bean
//    public JwtTokenAuthenticationFilter authenticationFilter() {
//        JwtTokenAuthenticationFilter filter = new JwtTokenAuthenticationFilter();
//        // filter.setAuthenticationManager(authenticationManager());
//        filter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
//        return filter;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.csrf().disable()
//                .authorizeRequests().antMatchers("**/rest/").authenticated()
//                .and()
//                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        http.headers().cacheControl();
////
////        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
////                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
////                .authorizeRequests()
////                .antMatchers(
////                        HttpMethod.GET,
////                        "/",
////                        "/webjars/**",
////                        "/*.html",
////                        "/favicon.ico",
////                        "/**/*.html",
////                        "/**/*.css",
////                        "/**/*.js"
////                ).permitAll()
////                // Allow pre-flight checks
////                .antMatchers(HttpMethod.OPTIONS).permitAll()
////                .antMatchers(HttpMethod.POST, "/registration/web/user").permitAll()
////                .antMatchers(HttpMethod.POST, "/login/web/user").permitAll()
////                .antMatchers(HttpMethod.GET, "/user/all/user").permitAll()
////                .antMatchers(HttpMethod.GET, "/user/find/by/userName").permitAll()
////                .antMatchers(HttpMethod.GET, "/user/find/by/uid").permitAll()
////                .antMatchers(HttpMethod.PUT, "/user/update").permitAll()
////                .antMatchers(HttpMethod.DELETE, "/user/delete").permitAll()
////                .anyRequest().authenticated().and()
////                .addFilterBefore(new TokenAuthenticationFilter(tokenHelper, userDetailsService), BasicAuthenticationFilter.class)
////                .logout()
////                .logoutRequestMatcher(new AntPathRequestMatcher("/api/auth/logout"))
////                .logoutSuccessHandler(logoutSuccess)
////                .deleteCookies(AUTH_TOKEN_COOKIE)
////                .and()
////                .addFilterBefore(corsFilter, ChannelProcessingFilter.class);
////
////        if (ignoreCsrfUris != null && !ignoreCsrfUris.trim().isEmpty()) {
////            http.csrf().ignoringAntMatchers(ignoreCsrfUris.split(",\\s*"));
////        }
////
////        // disable csrf for the login request
////        http.csrf()
////                /*--------------start----------------*/
////                .ignoringAntMatchers("/api/auth/login")
////                .ignoringAntMatchers("/api/users")
////                .ignoringAntMatchers("/api/users/{email}/forgot-password")
////                .ignoringAntMatchers("/api/users/reset-forgot-password")
////                .ignoringAntMatchers("/api/users/activate")
////                .ignoringAntMatchers("/api/users/{email}/profile-activation-email-resend")
////                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//    }
}
