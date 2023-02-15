package xyz.ganghua.service.impl;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import xyz.ganghua.annotatoin.MyFile;

@Service
public class AService {

    public static void main(String[] args) {

        ClassLoader classLoader = AService.class.getClassLoader();
        try {
            Class<?> loadClass = classLoader.loadClass("xyz.ganghua.service.impl.BService");
            if (loadClass.isAnnotationPresent(MyFile.class)) {
                System.out.println(loadClass.getConstructor().getName());
            }
            Method[] methods = loadClass.getMethods();
            for (Method m : methods) {
                if (m.isAnnotationPresent(MyFile.class)) {
                    System.out.println(m + "：  这个上面有注解");
                }
            }

            System.out.println(Arrays.toString(methods));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
