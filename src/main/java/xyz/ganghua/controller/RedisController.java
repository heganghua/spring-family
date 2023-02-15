package xyz.ganghua.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate<String, Object> rt;

    @GetMapping("/testredis")
    public String testRedis() {
        rt.opsForValue().set("key:controller:001", "测试数据！！！ 001", 500, TimeUnit.SECONDS);
        System.out.println("======" + rt.opsForValue().get("key:controller:001"));
        return "hello Redis!";
    }
}
