package com.mistar.memo.application.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.mistar.memo.core.security.JwtAuthenticationFilter
import com.mistar.memo.core.security.JwtTokenProvider
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val objectMapper: ObjectMapper
) : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
            .cors().and()
            .formLogin().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
            .antMatchers("/v1/auth/**").permitAll()
            .antMatchers("/v1/memos/**").hasRole("USER")
            .antMatchers("/v1/admin/**").hasRole("ADMIN")
            .anyRequest().hasRole("ADMIN")
            .and()
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider, objectMapper),
                UsernamePasswordAuthenticationFilter::class.java
            )
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/v3/api-docs", "/swagger-resources/**",
            "/swagger-ui/index.html", "/webjars/**", "/swagger/**"
        )
    }
}