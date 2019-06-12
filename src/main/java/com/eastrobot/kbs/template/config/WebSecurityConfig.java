package com.eastrobot.kbs.template.config;

import com.eastrobot.kbs.template.auth.KbsAuthenticationFailureHandler;
import com.eastrobot.kbs.template.auth.KbsAuthenticationSuccessHandler;
import com.eastrobot.kbs.template.auth.KbsLogoutSuccessHandler;
import com.eastrobot.kbs.template.auth.filter.KbsJwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationProvider KbsAuthenticationProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private KbsAuthenticationSuccessHandler kbsAuthenticationSuccessHandler;

    @Autowired
    private KbsAuthenticationFailureHandler kbsAuthenticationFailureHandler;

    @Autowired
    private KbsLogoutSuccessHandler kbsLogoutSuccessHandler;

    @Autowired
    private KbsJwtAuthorizationFilter kbsJwtAuthorizationFilter;

    @Override
    public void configure(WebSecurity web) {
        //忽略 swagger
        web.ignoring().antMatchers("/api-docs", "/swagger-resources/**", "/swagger-ui.html**", "/webjars/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 提供内存鉴权用户访问swagger
        auth.inMemoryAuthentication().withUser("swagger").password("swagger").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .authenticationProvider(KbsAuthenticationProvider)
                // .exceptionHandling()
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                // 登录配置
                .formLogin()
                .successHandler(kbsAuthenticationSuccessHandler)
                .failureHandler(kbsAuthenticationFailureHandler)
                .and()
                // 登出配置
                .logout()
                .logoutSuccessHandler(kbsLogoutSuccessHandler)
                // .deleteCookies(jwtConfig.getAuthHeader())
                .permitAll().and()
                .addFilterAfter(kbsJwtAuthorizationFilter, BasicAuthenticationFilter.class)
                // jwt不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许跨域访问的域名
        corsConfiguration.addAllowedOrigin("*");
        // 请求头
        corsConfiguration.addAllowedHeader("*");
        // 请求方法
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        corsConfiguration.addAllowedMethod(HttpMethod.POST);
        corsConfiguration.addAllowedMethod(HttpMethod.GET);
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
        // 预检请求的有效期，单位为秒。
        corsConfiguration.setMaxAge(3600L);
        // 是否支持安全证书
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}