package xyz.ganghua.interview;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Lingshi {
    public static void main(String[] args) {
         String str = "中文123AGBjkd8还有993";
        // statistics(str);
        //
         Map<String, Integer> hashMap = new HashMap<String, Integer>();
         hashMap.put("strCount", 0);
         hashMap.put("numberCount", 0);
         hashMap.put("chineseCount", 0);
         Map<String, Integer> recursion = recursion(str, hashMap);
         System.out.println(recursion.toString());

//        try {
//            writeStr("我是一列竖着的测试字符串!", "C:\\test\\test.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }s

        String str = "(500CNY+200CNY)*4|(100USD+20USD)*2";
        System.out.println(calPrice(str));

    }

    public static BigDecimal calPrice(String strs){

        BigDecimal result = new BigDecimal(0);
        if(null == strs){
            return result;
        }
        String[] strSplit = strs.split("\\|");

        for(int i=0; i<strSplit.length;  i++){
            BigDecimal price = new BigDecimal(0);
            String[] splits = strSplit[i].split("\\*");
            String left = splits[0].replace("(", "").replace(")","");

            String[] items = left.split("\\+");
            if(left.indexOf("CNY")!=-1){
                price = new BigDecimal(Arrays.stream(items).mapToInt(item -> Integer.valueOf(item.replace("CNY", ""))).sum());
                System.out.println(price);
            }else{
                BigDecimal adultBigDecimal = new BigDecimal(Arrays.stream(items).mapToInt(item -> Integer.valueOf(item.replace("USD", ""))).sum());
                price = adultBigDecimal.multiply(new BigDecimal(6.82432));
                System.out.println(price);
            }
            price = price.multiply(new BigDecimal(splits[1]));
            result = result.add(price);
        }
        return result.setScale(0, BigDecimal.ROUND_UP);
    }

    public static void writeStr(String str, String filePath) throws IOException {
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        int i = 0;
        while (i < str.length()) {
            bufferedWriter.write(str.charAt(i));
            bufferedWriter.newLine();
            ++i;
        }
        bufferedWriter.close();
    }

    private static final String regexC = "[\u4e00-\u9fa5]+";
    private static final String regexN = "[0-9]+";
    private static final String regexS = "[a-zA-Z]+";

    public static Map<String, Integer> recursion(String str, Map<String, Integer> hashMap) {

        if (str.isEmpty() || str.length() < 1) {
            return hashMap;
        }

        char item = str.charAt(0);
        String b = Character.toString(item);

        if (b.matches(regexS)) {
            hashMap.put("strCount", hashMap.get("strCount") + 1);
        } else if (b.matches(regexN)) {
            hashMap.put("numberCount", hashMap.get("numberCount") + 1);
        } else if (b.matches(regexC)) {
            hashMap.put("chineseCount", hashMap.get("chineseCount") + 1);
        }

        return recursion(str.substring(1), hashMap);
    }

    public static void statistics(String str) {

        String regexC = "[\u4e00-\u9fa5]+";
        String regexN = "[0-9]+";
        String regexS = "[a-zA-Z]+";
        int numberCount = 0;
        int chineseCount = 0;
        int strCount = 0;
        int i = 0;
        while (i < str.length()) {
            char item = str.charAt(i);
            String b = Character.toString(item);
            if (b.matches(regexS)) {
                ++strCount;
            } else if (b.matches(regexN)) {
                ++numberCount;
            } else if (b.matches(regexC)) {
                ++chineseCount;
            }
            ++i;
        }
        System.out.println("chineseCount: " + chineseCount + " strCount: " + strCount + " numberCount: " + numberCount);
    }

}
