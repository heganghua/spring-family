package xyz.ganghua.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * 
 * 1、分布式锁的KEY怎么设计<br>
 * 
 * @author ganghua
 * @date 2023/02/17
 */
@Service
public class RedisServiceImpl {

    private final static String REDISKEY = "LOCK:001";
    private final static long LOCK_EXPIRE = 30 * 1000L;// 单个业务持有锁的时间30s，防止死锁
    private final static long LOCK_TRY_INTERVAL = 30L;// 默认30ms尝试一次
    private final static long LOCK_TRY_TIMEOUT = 20 * 1000L;// 默认尝试20s

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public String seckillMaotai() {
        ValueOperations<String, Object> opv = redisTemplate.opsForValue();
        Boolean setIfAbsent = opv.setIfAbsent(REDISKEY, REDISKEY, LOCK_EXPIRE, TimeUnit.SECONDS);
        if (setIfAbsent) {
            try {
                // do something 业务处理
                Thread.sleep(3000L);
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                String unlocklua =
                    "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end;";
                redisTemplate.execute(new RedisCallback<Boolean>() {
                    @Override
                    public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                        Boolean eval = redisConnection.eval(unlocklua.getBytes(), ReturnType.BOOLEAN, 1,
                            REDISKEY.getBytes(), REDISKEY.getBytes());
                        return eval;
                    }
                });

            }
            return "Successfly!";
        } else {
            return "unsuccessfly!!";
        }

    }

}
