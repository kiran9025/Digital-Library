package org.digitalLibrary.Configuration;

import org.digitalLibrary.Services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserService userService;

    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/user/getallusers").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/book/*").hasAnyAuthority("ADMIN")
                        .requestMatchers("/user/adduser").hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin/alter-sequence").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/membership/**").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults())
                .build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(){
        ClientRegistration clientRegistration = ClientRegistration
                .withRegistrationId("github")
                .clientId("Ov23liLkCOUW29F2dYr7")
                .clientSecret("48dab3f26900c21a713ba3c41ee24e33652fa3a2")
                .scope("read:user")
                .userNameAttributeName("login")
                .authorizationUri("https://github.com/login/oauth/authorize")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .tokenUri("https://github.com/login/oauth/access_token")
                .userInfoUri("https://api.github.com/user")
                .redirectUri("http://localhost:8080/login/oauth2/code/github")
                .clientName("GitHub")
                .build();

        return new InMemoryClientRegistrationRepository(clientRegistration);
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(this.userService);
        return provider;
    }



}

