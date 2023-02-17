package xyz.ganghua.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ThreadLocal 线程问题， 访问ThreadLocal变量的线程都会有一份本地变量，解决线程安全问题
 * 
 * @author ganghua
 * @date 2023/02/16
 */
public class DataUtils {
    // 多线程访问下，SimpleDateFormat 会抛错，因为不是线程安全的，以共享变量出现时，并发线程场景下会报错
    // private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static ThreadLocal<SimpleDateFormat> sdf2 =
        ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static Date parse(String dateString) {
        Date parse = null;
        try {
            parse = sdf2.get().parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }
}
