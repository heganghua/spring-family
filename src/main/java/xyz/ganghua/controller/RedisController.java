package xyz.ganghua.controller;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.ganghua.redis.redisson.AquiredLockWorker;
import xyz.ganghua.redis.redisson.DistributedLocker;
import xyz.ganghua.redis.redisson.UnableToAquireLockException;
import xyz.ganghua.service.impl.RedisServiceImpl;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate<String, Object> rt;

    @Resource
    private RedisServiceImpl redisServiceimpl;

    @Autowired
    private DistributedLocker distributedLocker;

    @GetMapping("/testredis")
    public String testRedis() {
        rt.opsForValue().set("key:controller:001", "测试数据！！！ 001", 500, TimeUnit.SECONDS);
        System.out.println("======" + rt.opsForValue().get("key:controller:001"));
        return "hello Redis!";
    }

    @GetMapping("/tryLock")
    public String testTryLock() {
        return redisServiceimpl.seckillMaotai();
    }

    @GetMapping("/testRedisson")
    public String testRedisson() throws UnableToAquireLockException, Exception {
        String lock = distributedLocker.lock("test", new AquiredLockWorker<String>() {
            @Override
            public String invokeAfterLockAquire() {
                try {
                    System.out.println("处理业务逻辑。。。。。");
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "业务逻辑处理结果";
            }
        });
        return "Hello world" + lock;
    }
}
