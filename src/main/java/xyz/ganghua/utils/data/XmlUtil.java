package xyz.ganghua.utils.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * XML工具类<br>
 * 提供XML文本，Java对象和JsonNode节点之间的互相转化
 *
 * @author Sine
 * @date 2018/09/14
 */
public class XmlUtil {
    private static final XmlMapper defaultXmlMapper = newDefaultMapper();
    private static volatile XmlMapper xmlMapper = null;

    /**
     * 初始化默认mapper
     */
    public static XmlMapper newDefaultMapper() {
        XmlMapper mapper = new XmlMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        return mapper;
    }

    /**
     * 注入自定义mapper
     */
    public static void setXmlMapper(XmlMapper mapper) {
        xmlMapper = mapper;
    }

    /**
     * 获得XmlMapper<br>
     * 用于Java对象和Xml文本之间的序列化与反序列化<br>
     * 可用setXmlMapper方法来设置一个自定义的实现
     *
     * @return xmlMapper
     */
    public static XmlMapper mapper() {
        if (xmlMapper == null) {
            return defaultXmlMapper;
        } else {
            return xmlMapper;
        }
    }

    /**
     * 生成Xml文本
     *
     * @param o              对象
     * @param prettyPrint    是否格式化输出
     * @param escapeNonASCII 是否转义非ASCII字符
     * @return
     */
    private static String generateXml(Object o, boolean prettyPrint, boolean escapeNonASCII) {
        try {
            ObjectWriter writer = mapper().writer();
            if (prettyPrint) {
                writer = writer.with(SerializationFeature.INDENT_OUTPUT);
            }
            if (escapeNonASCII) {
                writer = writer.with(Feature.ESCAPE_NON_ASCII);
            }
            return writer.writeValueAsString(o);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 过滤输入文本中的控制字符
     *
     * @param content 输入文本
     * @return 过滤后的文本
     */
    public static String wrapXmlContent(String content) {
        StringBuffer appender = new StringBuffer("");

        if ((content != null) && (!content.trim().isEmpty())) {
            appender = new StringBuffer(content.length());

            for (int i = 0; i < content.length(); i++) {
                char ch = content.charAt(i);
                if ((ch == '\t') || (ch == '\n') || (ch == '\r') ||
                        ((ch >= ' ') && (ch <= 55295)) ||
                        ((ch >= 57344) && (ch <= 65533)) || (
                        (ch >= 65536) && (ch <= 1114111))) {
                    appender.append(ch);
                }
            }
        }

        return appender.toString();
    }

    /**
     * 转换Java对象为JsonNode节点
     *
     * @param data 待转换的Java对象
     */
    public static JsonNode toJsonNode(final Object data) {
        try {
            return mapper().valueToTree(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转换JsonNode节点为Java对象
     *
     * @param jsonNode 待转换的JsonNode节点
     * @param clazz    待输出的Java对象类型
     */
    public static <A> A fromJsonNode(JsonNode jsonNode, Class<A> clazz) {
        try {
            return mapper().treeToValue(jsonNode, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转换JsonArrayNode节点为Java对象List<br>
     * 如果传入节点并不是Array，则直接尝试转为Java对象后，放入List内返回
     *
     * @param jsonNodeArray 待转换的JsonNodeArray节点
     * @param clazz         待输出List中的Java对象类型
     * @return
     */
    public static <A> List<A> fromJsonArray(JsonNode jsonNodeArray, Class<A> clazz) {
        List<A> list = new ArrayList<>();
        if (jsonNodeArray.isArray()) {
            for (JsonNode node : jsonNodeArray) {
                A item = fromJsonNode(node, clazz);
                list.add(item);
            }
        } else {
            A item = fromJsonNode(jsonNodeArray, clazz);
            list.add(item);
        }
        return list;
    }

    /**
     * 创建一个新的空ObjectNode节点
     */
    public static ObjectNode newObject() {
        return mapper().createObjectNode();
    }

    /**
     * 创建一个新的空ArrayNode节点
     */
    public static ArrayNode newArray() {
        return mapper().createArrayNode();
    }

    /**
     * 将Java对象输出为单行xml文档
     */
    public static String stringifyXml(Object obj) {
        return generateXml(obj, false, false);
    }

    /**
     * 将Java对象输出为单行Xml文本<br>
     * 跳过非ASCII字符
     */
    public static String asciiStringify(Object obj) {
        return generateXml(obj, false, true);
    }

    /**
     * 将Java对象输出为格式化后带缩进的xml文档
     */
    public static String prettyPrint(Object obj) {
        return generateXml(obj, true, false);
    }

    /**
     * 解析Xml文本为JsonNode节点
     */
    public static JsonNode parse(String src) {
        try {
            return mapper().readTree(src);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * 从inputStream流中读取Xml文本，解析为JsonNode节点
     */
    public static JsonNode parse(java.io.InputStream src) {
        try {
            return mapper().readTree(src);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * 解析Xml文本的byte数组为JsonNode节点
     */
    public static JsonNode parse(byte[] src) {
        try {
            return mapper().readTree(src);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
