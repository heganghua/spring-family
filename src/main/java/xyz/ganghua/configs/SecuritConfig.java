package xyz.ganghua.configs;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuritConfig /*extends WebSecurityConfigurerAdapter*/ {

    // @Autowired
    // private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    //
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    // }
    //
    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    // http
    // // 关闭CSRF
    // .csrf().disable()
    // // 不通过session获取SecurityContext
    // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
    // // 开发登陆接口，允许匿名访问
    // .antMatchers("/user/login").anonymous()
    // // 除登录接口外的所有请求全部需要鉴权认证
    // .anyRequest().authenticated();
    //
    // // 在UsernamePasswordAuthenticationFilter过滤器之前，添加新过滤器
    // http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    // }
    //
    // @Bean
    // @Override
    // protected AuthenticationManager authenticationManager() throws Exception {
    // return super.authenticationManager();
    // }

}
