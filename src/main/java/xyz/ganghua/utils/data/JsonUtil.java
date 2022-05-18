package xyz.ganghua.utils.data;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Json工具类<br>
 * 提供Json文本，Java对象和JsonNode节点之间的互相转化<br/>
 * <a href="http://redmine.jxc1688.cn/projects/jxzj-utils-common/wiki/Json工具类">Wiki详细说明</a>
 * 
 * @author Sine
 * @date 2018/10/12
 */
public class JsonUtil {

    private static final JsonUtilCustomized INSTANCE;
    static {
        INSTANCE = new JsonUtilCustomized(newDefaultMapper());
    }

    /**
     * 初始化mapper
     */
    private static ObjectMapper newDefaultMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // JDK8支持
        mapper.registerModule(new Jdk8Module());
        // JDK8新时间对象支持
        mapper.registerModule(new JavaTimeModule());

        // 默认地区和时区设置
        mapper.setLocale(Locale.CHINA);
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        // 允许前导0
        mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        // 序列化时略过值为null的属性
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 反序列化时略过未知的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 枚举处理特性。反序列化时，未知的枚举值属性将被处理为null，或是@JsonEnumDefaultValue指定的默认值，而不报错
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);

        return mapper;
    }

    /**
     * 获取ObjectMapper
     */
    public static ObjectMapper getMapper() {
        return INSTANCE.getMapper();
    }

    /**
     * 创建一个新的空ObjectNode节点
     */
    public static ObjectNode newObject() {
        return INSTANCE.newObject();
    }

    /**
     * 创建一个新的空ArrayNode节点
     */
    public static ArrayNode newArray() {
        return INSTANCE.newArray();
    }

    /**
     * 转换Json字符串为Java对象(
     * 
     * @param content 输入Json字符串
     * @param clazz 待输出的Java对象类型
     * @return
     */
    public static <T> T fromJsonString(String content, Class<T> clazz) {
        return INSTANCE.fromJsonString(content, clazz);
    }

    /**
     * 转换JsonNode节点为Java对象
     *
     * @param jsonNode 待转换的JsonNode节点
     * @param clazz 待输出的Java对象类型
     */
    public static <T> T fromJsonNode(JsonNode jsonNode, Class<T> clazz) {
        return INSTANCE.fromJsonNode(jsonNode, clazz);
    }

    /**
     * 转换Json字符串为Map对象
     * 
     * @param content 输入Json字符串
     * @param mapClazz Map类型
     * @param keyClazz Key类型
     * @param valueClazz Value类型
     * @return
     */
    public static <K, V> Map<K, V> fromJsonStringToMap(String content, Class<? extends Map<K, V>> mapClazz,
        Class<K> keyClazz, Class<V> valueClazz) {
        return INSTANCE.fromJsonStringToMap(content, keyClazz, valueClazz, mapClazz);
    }

    /**
     * 转换JsonArrayNode节点为Java对象List<br>
     * 如果传入节点并不是Array，则直接尝试转为Java对象后，放入List内返回
     * 
     * @param jsonNodeArray 待转换的JsonNodeArray节点
     * @param clazz 待输出List中的Java对象类型
     * @return
     */
    public static <T> List<T> fromJsonArray(JsonNode jsonNodeArray, Class<T> clazz) {
        return INSTANCE.fromJsonArray(jsonNodeArray, clazz, null);
    }

    /**
     * 转换JsonArrayNode节点为Java对象List<br>
     * 如果传入节点并不是Array，则直接尝试转为Java对象后，放入List内返回
     * 
     * @param jsonNodeArray 待转换的JsonNodeArray节点
     * @param clazz 待输出List中的Java对象类型
     * @param listClazz 输出List类型,传null为默认ArrayList
     * @return
     */
    public static <T> List<T> fromJsonArray(JsonNode jsonNodeArray, Class<T> clazz,
        Class<? extends List<T>> listClazz) {
        return INSTANCE.fromJsonArray(jsonNodeArray, clazz, listClazz);
    }

    /**
     * 转换Java对象为JsonNode节点
     *
     * @param obj 待转换的Java对象
     */
    public static JsonNode toJsonNode(final Object obj) {
        return INSTANCE.toJsonNode(obj);
    }

    /**
     * 将Java对象输出为单行Json文本，捕获异常
     * 
     * @param obj 待输出的Java对象
     */
    public static String toString(Object obj) {
        return INSTANCE.toString(obj);
    }

    /**
     * 将Java对象输出为单行Json文本
     * 
     * @param obj 待输出的Java对象
     */
    public static String stringify(Object obj) {
        return INSTANCE.stringify(obj);
    }

    /**
     * 将Java对象输出为单行Json文本<br>
     * 转义非ASCII字符
     * 
     * @param obj 待输出的Java对象
     */
    public static String asciiStringify(Object obj) {
        return INSTANCE.asciiStringify(obj);
    }

    /**
     * 将Java对象输出为格式化后带缩进的Json文本
     * 
     * @param obj 待输出的Java对象
     */
    public static String prettyPrint(Object obj) {
        return INSTANCE.prettyPrint(obj);
    }

    /**
     * 解析Json文本为JsonNode节点
     */
    public static JsonNode parse(String src) {
        return INSTANCE.parse(src);
    }

    /**
     * 从inputStream流中读取Json文本，解析为JsonNode节点
     */
    public static JsonNode parse(InputStream src) {
        return INSTANCE.parse(src);
    }

    /**
     * 解析Json文本的byte数组为JsonNode节点
     */
    public static JsonNode parse(byte[] src) {
        return INSTANCE.parse(src);
    }

    /**
     * 通过Json反序列化 -> 序列化，转换对象为map<br>
     * map使用HashMap<String, String>
     * 
     * @param bean java对象
     * @return
     */
    public static Map<String, String> convertToMap(Object obj) {
        return convertToMap(obj, String.class, String.class, HashMap.class);
    }

    /**
     * 通过Json反序列化 -> 序列化，转换对象为map<br>
     * map使用HashMap
     * 
     * @param <K> mapKey类型
     * @param <V> mapValue类型
     * @param bean java对象
     * @param keyClazz mapKey类型
     * @param valueClazz mapValue类型
     * @return
     */
    public static <K, V> Map<K, V> convertToMap(Object obj, Class<K> keyClazz, Class<V> valueClazz) {
        return convertToMap(obj, keyClazz, valueClazz, HashMap.class);
    }

    /**
     * 通过Json反序列化 -> 序列化，转换对象为map<br>
     * 自定义map类型
     * 
     * @param <K> mapKey类型
     * @param <V> mapValue类型
     * @param bean java对象
     * @param keyClazz mapKey类型
     * @param valueClazz mapValue类型
     * @param mapClazz map类型
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <K, V> Map<K, V> convertToMap(Object obj, Class<K> keyClazz, Class<V> valueClazz,
        Class<? extends Map> mapClazz) {
        MapType type = getMapper().getTypeFactory().constructMapType(mapClazz, keyClazz, valueClazz);
        return getMapper().convertValue(obj, type);
    }

    /**
     * 通过Json反序列化 -> 序列化，深拷贝对象
     * 
     * @param bean
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T copy(T bean) {
        if (bean == null) {
            return bean;
        }
        if (bean.getClass().isPrimitive()) {
            return bean;
        }
        String json = stringify(bean);
        return (T)JsonUtil.fromJsonString(json, bean.getClass());
    }
}
