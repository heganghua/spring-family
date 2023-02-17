package xyz.ganghua.redis.redisson;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisLocker implements DistributedLocker {

    private final static String LOCKER_PREFIX = "lock:";

    private final static long LOCKER_WAITTIME = 100L;

    @Autowired
    private RedissonConnector redissonconnector;

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception {
        return lock(resourceName, worker, 100);
    }

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime)
        throws UnableToAquireLockException, Exception {
        RedissonClient redisson = redissonconnector.getRedisson();
        RLock lock = redisson.getLock(LOCKER_PREFIX + resourceName);
        if (lock.tryLock(LOCKER_WAITTIME, lockTime, TimeUnit.SECONDS)) {
            try {
                return worker.invokeAfterLockAquire();
            } catch (Exception e) {
                System.out.println("业务逻辑异常： " + e);
            } finally {
                lock.unlock();
            }
        }
        throw new UnableToAquireLockException();
    }

}
