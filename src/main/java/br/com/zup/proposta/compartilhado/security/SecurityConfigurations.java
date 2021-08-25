package br.com.zup.proposta.compartilhado.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    private final String auth = "SCOPE_escopo-proposta";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                authorizeRequests
                        .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/actuator").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/proposta/**").hasAnyAuthority(auth)
                        .antMatchers(HttpMethod.POST, "/api/proposta/**").hasAnyAuthority(auth)
                        .antMatchers(HttpMethod.POST, "/api/cartao/**").hasAnyAuthority(auth)
                        .antMatchers(HttpMethod.PATCH, "/api/cartao/**").hasAnyAuthority(auth)
                        .anyRequest().authenticated())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
