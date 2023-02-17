package xyz.ganghua.redis.redisson;

/**
 * 获取锁后需要处理的业务逻辑
 * 
 * @author ganghua
 * @date 2023/02/17
 */
public interface AquiredLockWorker<T> {

    T invokeAfterLockAquire() throws Exception;
}
