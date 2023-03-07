package xyz.ganghua;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;

@SpringBootApplication
@MapperScan("xyz.ganghua.dao")
public class SpringFamilyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringFamilyApplication.class, args);
    }

    @Bean
    public HandlerInterceptor getActionInterceptor() {
        return null;
    }
}
