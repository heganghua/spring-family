package xyz.ganghua.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * 常用IO <br>
 * 1、字节流<br>
 * InputStream <br>
 * - FileInputStream<br>
 * - BufferedInputStream<br>
 * 
 * OutputStream<br>
 * - FileOutPutStream<br>
 * - BufferedINputStream<br>
 * 
 * 2、字符流<br>
 * - 转换流：InputStreamReader - FileReader<br>
 * - BufferedReader
 * 
 * - 转换流 OutputStreamWriter - FileWriter<br>
 * - BufferedWrider
 * 
 * 
 * @author ganghua
 * @date 2023/02/07
 */
public class InputStreamDemo {
    public static void main(String[] args) {
        InputStreamDemo.test3ByByte();
    }

    /**
     * 字符流
     */
    public static void test2ByReader() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("./img/pingan.cookies.txt")));
            String str = null;
            String s = null;
            while ((s = br.readLine()) != null) {
                str += s;
            }

            br.close();
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字节流
     */
    public static void test3ByByte() {
        try {
            FileInputStream bi = new FileInputStream(new File("./img/pingan.cookies.txt"));
            byte[] b = new byte[8192];
            int end = 0;
            String st = new String();
            while ((end = bi.read(b)) != -1) {
                st += new String(b);
            }
            System.out.println(st);
            bi.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void test1() {
        Scanner scanner = new Scanner(System.in);
        try {
            FileInputStream fileInputStream = new FileInputStream("F:\\tempFile\\2.jpg");
            BufferedInputStream bis = new BufferedInputStream(fileInputStream);
            byte[] buf = new byte[1024];
            int len = 0;

            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("f:/tempFile/4.jpg"));
            while ((len = bis.read(buf)) != -1) {
                System.out.println(new String(buf));
                out.write(buf);
            }
            fileInputStream.close();
            bis.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanner.close();
    }

    /**
     * 转换流
     * 
     * @throws FileNotFoundException
     */
    public void inputStreamReadToWriter() throws FileNotFoundException {

        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(""));
    }
}
