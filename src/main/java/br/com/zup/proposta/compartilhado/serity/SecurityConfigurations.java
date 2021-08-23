package br.com.zup.proposta.compartilhado.serity;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                authorizeRequests
                        .antMatchers(HttpMethod.GET, "/actuator/**").hasAuthority("SCOPE_escopo-proposta")
                        .antMatchers(HttpMethod.GET, "/api/proposta/**").hasAnyAuthority("SCOPE_escopo-proposta")
                        .antMatchers(HttpMethod.POST, "/api/proposta/**").hasAnyAuthority("SCOPE_escopo-proposta")
                        .antMatchers(HttpMethod.POST, "/api/cartao/**").hasAnyAuthority("SCOPE_escopo-proposta")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

    }

}
