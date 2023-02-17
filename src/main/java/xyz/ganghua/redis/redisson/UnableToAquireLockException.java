package xyz.ganghua.redis.redisson;

public class UnableToAquireLockException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnableToAquireLockException() {

    }

    public UnableToAquireLockException(String Message) {

    }

    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }

}
