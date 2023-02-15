package xyz.ganghua.gc;

public class GcTest {

    public static void main(String[] args) {
        // Object object = new Object();
        // System.out.println(object);
        // System.gc();
        // object = new Object();
        // object = new Object();
        // System.gc();
        // System.out.println(object);

        Integer M = new Integer(1024 * 1024 * 1); // 单位, 兆(M)
        byte[] bytes = new byte[1 * M]; // 申请 1M 大小的内存空间
        bytes = null; // 断开引用链
        System.gc(); // 通知 GC 收集垃圾
        System.out.println();
        bytes = new byte[1 * M]; // 重新申请 1M 大小的内存空间
        bytes = new byte[1 * M]; // 再次申请 1M 大小的内存空间
        System.gc();
        System.out.println();

    }

}
