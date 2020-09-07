package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/h2-console/**").permitAll()
                        .antMatchers("/", "/webjars/**", "/login","/resources/**").permitAll()
                        .mvcMatchers("/beers/find", "/beers/{beerId}").hasAnyRole("ADMIN","CUSTOMER","USER")
                        .antMatchers(HttpMethod.GET,"/brewery/api/v1/breweries").hasAnyRole("ADMIN","CUSTOMER")
                        .antMatchers(HttpMethod.GET,"/brewery/breweries/**").hasAnyRole("ADMIN","CUSTOMER")
                        .antMatchers(HttpMethod.GET,"/api/v1/beer/**").permitAll()
                        .mvcMatchers(HttpMethod.DELETE,"/api/v1/beer/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/api/v1/beer/**").hasAnyRole("ADMIN","CUSTOMER","USER")
                        .mvcMatchers(HttpMethod.GET,"/api/v1/beerUpc/{upc}").hasAnyRole("ADMIN","CUSTOMER","USER")
                )
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic().and()
                .csrf().disable();

        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
