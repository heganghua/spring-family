package xyz.ganghua.redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RedissonConnector {

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.url}")
    private String url;

    @Value("${spring.redis.database}")
    private int database;

    // private RedissonClient redisson;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient singleRedissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress(url).setPassword(password).setDatabase(database);

        // 调用无参的create()方法，默认是连接127.0.0.1:6379
        return Redisson.create(config);
    }

}
