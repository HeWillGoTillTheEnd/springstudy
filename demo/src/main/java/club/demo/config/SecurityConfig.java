package club.demo.config;

import club.demo.security.filter.ApiCheckFilter;
import club.demo.security.filter.ApiLoginFilter;
import club.demo.security.handler.ApiLoginFailHandler;
import club.demo.security.handler.ClubLoginSuccessHandler;
import club.demo.security.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig{



    public JWTUtil jwtUtil(){
        return new JWTUtil();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(){
//        UserDetails user = User.builder()
//                .username("user1")
//                .password(passwordEncoder().encode("1111"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((auth) -> {
//            auth.antMatchers("/sample/all").permitAll()
//                    .antMatchers("/sample/member").hasRole("USER")
//                    .antMatchers("/sample/admin").hasRole("ADMIN");
//        });
        log.info("---------------------------------------------------------filterchain--------------------------");

        http.formLogin();
        http.csrf().disable();
        http.logout();
        http.oauth2Login().successHandler(clubLoginSuccessHandler());
        http.rememberMe().tokenValiditySeconds(60 * 60 * 24 * 7);
        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        //반드시 필요
        http.authenticationManager(authenticationManager);

        //APILoginFilter

        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(apiLoginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    public ApiLoginFilter apiLoginFilter(AuthenticationManager authenticationManager) {
        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login",jwtUtil());
        apiLoginFilter.setAuthenticationManager(authenticationManager);
        return apiLoginFilter;
    }

    @Bean
    public ClubLoginSuccessHandler clubLoginSuccessHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }
    @Bean
    public ApiCheckFilter apiCheckFilter(){
        return new ApiCheckFilter("/notes/**/*",jwtUtil());
    }


}
