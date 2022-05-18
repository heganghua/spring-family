package xyz.ganghua.utils.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 自定义ObjectMapper对象的Json工具类<br>
 * 提供Json文本，Java对象和JsonNode节点之间的互相转化<br/>
 * <a href="http://redmine.jxc1688.cn/projects/jxzj-utils-common/wiki/Json工具类">Wiki详细说明</a>
 * 
 * @author Sine
 */
public class JsonUtilCustomized {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtilCustomized.class);

    private final ObjectMapper mapper;

    /**
     * 构造方法
     * 
     * @param mapper
     */
    public JsonUtilCustomized(ObjectMapper mapper) {
        if (null == mapper) {
            LOGGER.error("初始化json工具类失败，objectMapper不能为空");
            throw new RuntimeException("Object Mapper can not be null");
        }
        this.mapper = mapper;
    }

    /**
     * 获取ObjectMapper
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * 创建一个新的空ObjectNode节点
     */
    public ObjectNode newObject() {
        return mapper.createObjectNode();
    }

    /**
     * 创建一个新的空ArrayNode节点
     */
    public ArrayNode newArray() {
        return mapper.createArrayNode();
    }

    /**
     * 转换Json字符串为Java对象
     * 
     * @param content 输入Json字符串
     * @param clazz 待输出的Java对象类型
     * @return
     */
    public <T> T fromJsonString(String content, Class<T> clazz) {
        if (content == null) {
            return null;
        }
        try {
            return mapper.readValue(content, clazz);
        } catch (IOException e) {
            throw new RuntimeException(content, e);
        }
    }

    /**
     * 转换JsonNode节点为Java对象
     *
     * @param jsonNode 待转换的JsonNode节点
     * @param clazz 待输出的Java对象类型
     */
    public <T> T fromJsonNode(JsonNode jsonNode, Class<T> clazz) {
        try {
            return mapper.treeToValue(jsonNode, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
    public <K, V> Map<K, V> fromJsonStringToMap(String content, Class<K> keyClazz, Class<V> valueClazz,
        Class<? extends Map<K, V>> mapClazz) {
        if (content == null) {
            return null;
        }
        try {
            return mapper.readValue(content, mapper.getTypeFactory().constructMapType(mapClazz, keyClazz, valueClazz));
        } catch (IOException e) {
            throw new RuntimeException(content, e);
        }
    }

    /**
     * 转换JsonArrayNode节点为Java对象List<br>
     * 如果传入节点并不是Array，则直接尝试转为Java对象后，放入List内返回
     * <p>
     * List的实现类型为ArrayList
     * 
     * @param jsonNodeArray 待转换的JsonNodeArray节点
     * @param clazz 待输出List中的Java对象类型
     * @return
     */
    public <T> List<T> fromJsonArray(JsonNode jsonNodeArray, Class<T> clazz) {
        return fromJsonArray(jsonNodeArray, clazz, null);
    }

    /**
     * 转换JsonArrayNode节点为Java对象List<br>
     * 如果传入节点并不是Array，则直接尝试转为Java对象后，放入List内返回
     * 
     * @param jsonNodeArray 待转换的JsonNodeArray节点
     * @param clazz 待输出List中的Java对象类型
     * @param collectionClazz 输出List类型,传null为默认ArrayList
     * @return
     */
    public <T> List<T> fromJsonArray(JsonNode jsonNodeArray, Class<T> clazz, Class<? extends List<T>> listClazz) {
        List<T> list = null;
        if (null == listClazz) {
            list = new ArrayList<>();
        } else {
            try {
                list = listClazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                list = new ArrayList<>();
            }
        }
        if (jsonNodeArray.isArray()) {
            for (JsonNode node : jsonNodeArray) {
                T item = fromJsonNode(node, clazz);
                list.add(item);
            }
        } else {
            T item = fromJsonNode(jsonNodeArray, clazz);
            list.add(item);
        }
        return list;
    }

    /**
     * 由java对象生成Json文本
     * 
     * @param obj 待转换的Java对象
     * @param prettyPrint 是否格式化输出
     * @param escapeNonASCII 是否转义非ASCII字符
     * @return
     */
    private String generateJsonStr(Object obj, boolean prettyPrint, boolean escapeNonASCII) {
        try {
            ObjectWriter writer = mapper.writer();
            if (prettyPrint) {
                writer = writer.with(SerializationFeature.INDENT_OUTPUT);
            }
            if (escapeNonASCII) {
                writer = writer.with(Feature.ESCAPE_NON_ASCII);
            }
            return writer.writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转换Java对象为JsonNode节点
     *
     * @param obj 待转换的Java对象
     */
    public JsonNode toJsonNode(final Object obj) {
        try {
            return mapper.valueToTree(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将Java对象输出为单行Json文本，捕获异常
     * 
     * @param obj 待输出的Java对象
     */
    public String toString(Object obj) {
        try {
            return stringify(obj);
        } catch (Exception e) {
            LOGGER.error("创建json文本失败", e);
        }
        return obj == null ? null : obj.toString();
    }

    /**
     * 将Java对象输出为单行Json文本
     * 
     * @param obj 待输出的Java对象
     */
    public String stringify(Object obj) {
        return generateJsonStr(obj, false, false);
    }

    /**
     * 将Java对象输出为单行Json文本<br>
     * 转义非ASCII字符
     * 
     * @param obj 待输出的Java对象
     */
    public String asciiStringify(Object obj) {
        return generateJsonStr(obj, false, true);
    }

    /**
     * 将Java对象输出为格式化后带缩进的Json文本
     * 
     * @param obj 待输出的Java对象
     */
    public String prettyPrint(Object obj) {
        return generateJsonStr(obj, true, false);
    }

    /**
     * 解析Json文本为JsonNode节点
     */
    public JsonNode parse(String src) {
        try {
            return mapper.readTree(src);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从inputStream流中读取Json文本，解析为JsonNode节点
     */
    public JsonNode parse(InputStream src) {
        try {
            return mapper.readTree(src);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析Json文本的byte数组为JsonNode节点
     */
    public JsonNode parse(byte[] src) {
        try {
            return mapper.readTree(src);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
