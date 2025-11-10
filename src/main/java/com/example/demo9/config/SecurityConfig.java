package com.example.demo9.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//    http
//            .formLogin(Customizer.withDefaults())
//            .logout(Customizer.withDefaults());

    // 사용자가 만든 로그인폼에 대해서만 허용처리
    http.formLogin(form -> form
            .loginPage("/member/memberLogin")
            .defaultSuccessUrl("/member/memberLoginOk", true)
            .failureUrl("/member/login/error")
            .usernameParameter("email")   // 시큐리트 입력 파라메터가 name인데, 이곳에선 email로 사용함
            .permitAll());

    // 각 페이지에 대한 접근 권한설정
    http.authorizeHttpRequests(request -> request
            .requestMatchers("/images/**", "message/**").permitAll()
            .requestMatchers("/", "/css/**", "/js/**", "/guest/**").permitAll()
            .requestMatchers("/member/memberJoin").permitAll()
            .requestMatchers("/member/memberLoginOk","/member/memberLogout","/member/memberMain").authenticated()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
    );

    // 권한 없는 user의 접근시 예외처리
    http.exceptionHandling(exception -> exception
            .accessDeniedPage("/error/accessDenied"));


    // 기본 로그아웃 처리
    http.logout(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
