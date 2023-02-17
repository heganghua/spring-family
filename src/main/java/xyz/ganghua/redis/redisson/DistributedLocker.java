package xyz.ganghua.redis.redisson;

/**
 * 获取锁管理类
 * 
 * @author ganghua
 * @date 2023/02/17
 */
public interface DistributedLocker {

    /**
     * 获取锁
     * 
     * @param <T>
     * @param resourceName 锁的名称
     * @param worker 获取锁后的处理类（业务逻辑）
     * @return 处理完业务逻辑需要返回的数据
     */
    <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception;

    <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime)
        throws UnableToAquireLockException, Exception;

}
