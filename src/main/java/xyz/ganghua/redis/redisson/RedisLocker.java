package xyz.ganghua.redis.redisson;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisLocker implements DistributedLocker {

    private final static String LOCKER_PREFIX = "lock:";

    private final static long LOCKER_WAITTIME = 3L;

    // @Autowired
    // private RedissonConnector redissonconnector;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception {
        return lock(resourceName, worker, 10);
    }

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime)
        throws UnableToAquireLockException, Exception {
        // RedissonClient redisson = redissonconnector.getRedisson();
        RLock lock = redissonClient.getLock(LOCKER_PREFIX + resourceName);

        // 尝试加锁，最多等待LOCKER_WAITTIME秒，上锁以后lockTime秒自动解锁
        if (lock.tryLock(LOCKER_WAITTIME, TimeUnit.SECONDS)) {
            try {
                return worker.invokeAfterLockAquire();
            } catch (Exception e) {
                System.out.println("业务逻辑异常： " + e);
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("排队中，获取锁失败。。。。");
        }
        throw new UnableToAquireLockException();
    }

}
