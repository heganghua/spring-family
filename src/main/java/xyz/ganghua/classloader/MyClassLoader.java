package xyz.ganghua.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class MyClassLoader extends ClassLoader {

    public MyClassLoader() {}

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file =
            new File("F:\\eclipse-workspace\\spring-family\\src\\main\\java\\xyz\\ganghua\\classloader\\Person.class");
        try {
            byte[] bytes = getClassBytes(file);
            // defineClass方法可以把二进制流字节组成的文件转换为一个java.lang.Class
            Class<?> c = this.defineClass(name, bytes, 0, bytes.length);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    private byte[] getClassBytes(File file) throws Exception {
        // 这里要读入.class的字节，因此要使用字节流
        FileInputStream fis = new FileInputStream(file);
        FileChannel fc = fis.getChannel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableByteChannel wbc = Channels.newChannel(baos);
        ByteBuffer by = ByteBuffer.allocate(1024);

        while (true) {
            int i = fc.read(by);
            if (i == 0 || i == -1)
                break;
            by.flip();
            wbc.write(by);
            by.clear();
        }
        fis.close();
        return baos.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        MyClassLoader mcl = new MyClassLoader();
        Class<?> clazz = mcl.loadClass("xyz.ganghua.classloader.Person");
        // Class<?> clazz = Class.forName("xyz.ganghua.classloader.Person", true, mcl);
        Object obj = clazz.newInstance();

        System.out.println(obj);
        // 打印出我们的自定义类加载器
        System.out.println(obj.getClass().getClassLoader());
    }
}
