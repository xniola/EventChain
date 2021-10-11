package it.univpm.eventchain.config;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import it.univpm.eventchain.auth.RestOAuth2AccessDeniedHandler;
import it.univpm.eventchain.auth.RestOAuth2AuthenticationEntryPoint;
import it.univpm.eventchain.auth.RestOAuth2AuthenticationFilter;
import it.univpm.eventchain.auth.RestOAuth2AuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
   prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   private final RestOAuth2AuthorizationFilter restfulOAuth2AuthorizationFilter;
   private final RestOAuth2AuthenticationFilter restfulOAuth2AuthenticationFilter;
      
   @Override
   protected final void configure(@NotNull final HttpSecurity http) throws Exception {
      Intrinsics.checkParameterIsNotNull(http, "http");
      http
      .cors().and()
      .csrf().disable()
      .addFilterBefore(restfulOAuth2AuthorizationFilter, BasicAuthenticationFilter.class)
      .addFilterBefore(restfulOAuth2AuthenticationFilter, BasicAuthenticationFilter.class)
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .exceptionHandling()
      .authenticationEntryPoint(new RestOAuth2AuthenticationEntryPoint())
      .accessDeniedHandler(new RestOAuth2AccessDeniedHandler())
      .and().authorizeRequests()
      //.antMatchers("/login.html").permitAll()
      .anyRequest().authenticated();
   }

   public SecurityConfig(@NotNull final RestOAuth2AuthorizationFilter restfulOAuth2AuthorizationFilter, @NotNull final RestOAuth2AuthenticationFilter restfulOAuth2AuthenticationFilter) {
	  super();
	  Intrinsics.checkParameterIsNotNull(restfulOAuth2AuthorizationFilter, "restfulOAuth2AuthorizationFilter");
      Intrinsics.checkParameterIsNotNull(restfulOAuth2AuthenticationFilter, "restfulOAuth2AuthenticationFilter");      
      this.restfulOAuth2AuthorizationFilter = restfulOAuth2AuthorizationFilter;
      this.restfulOAuth2AuthenticationFilter = restfulOAuth2AuthenticationFilter;
   }
}
