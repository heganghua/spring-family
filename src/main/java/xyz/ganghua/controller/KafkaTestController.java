package xyz.ganghua.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaUtils;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.ganghua.utils.SpringKafkaUtils;
import xyz.ganghua.vo.KafkaResp;

@RestController
@RequestMapping("/kafka")
public class KafkaTestController {

    private final static String TOPIC = "TOPIC_INPUT";
    private final Logger logger = LoggerFactory.getLogger(KafkaTestController.class);

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @Autowired
    private SpringKafkaUtils springKafkaUtils;

    /**
     * 获取所有topic
     * 
     * @return
     */
    @GetMapping("/get/topics")
    public KafkaResp getTopciAll() {
        List<String> allTopic = springKafkaUtils.getAllTopic();
        KafkaResp kafkaResp = new KafkaResp();
        kafkaResp.setLists(allTopic);
        kafkaResp.success();
        return kafkaResp;
    }

    /**
     * 查询topic信息 (支持批量，这里就单个作为演示)
     *
     * @param topic 自增主键
     * @return ResponseVo
     */
    @GetMapping("get/{topic}")
    public KafkaResp getBytTopic(@PathVariable String topic) {

        String consumerGroupId = KafkaUtils.getConsumerGroupId();
        System.out.println(">>consumerGroupId:  " + consumerGroupId);
        List<String> asList = Arrays.asList(topic);
        // 转换成线程安全类
        List<String> synchronizedList = Collections.synchronizedList(asList);

        String topicInfo = springKafkaUtils.getTopicInfo(asList);
        KafkaResp kafkaResp = new KafkaResp();
        kafkaResp.success(topicInfo);
        return kafkaResp;
    }

    /**
     * 普通方法测试
     * 
     * @param input
     */
    @GetMapping("/send/{input}")
    public void sendFoo(@PathVariable String input) {
        kafkaTemplate.send(TOPIC, input);
    }

    /**
     * kafkaListener监听组 topic
     * 
     * @param input
     */
    @KafkaListener(id = "webGroup", topics = TOPIC)
    public void listen(String input) {
        logger.info("input value: {}", input);
    }

    /**
     * kafkaListener监听组 topic
     * 
     * @param input
     */
    @KafkaListener(id = "kafkaLog", topics = "kafka-log")
    public void listenKafkaLogs(String input) {
        System.out.println("success往kafka输送日志： " + input);
    }

    @GetMapping("/logs")
    public void testLog4j2ToKafka() {
        for (int i = 0; i < 100; i++) {
            logger.warn("测试细腻。。。。。。,这是往日志信息里面写的东西，会输出到kafka里面topic为 kafka-log里面： " + i);
        }
    }

    /**
     * kafka回调方法测试
     * 
     * @param message
     */
    @GetMapping("/sendCallback/{message}")
    public void callbackSend(@PathVariable String message) {
        kafkaTemplate.send(TOPIC, message).addCallback(success -> {

            // 消息发送到的topic
            String topic = success.getRecordMetadata().topic();
            // 消息发送到的分区
            Integer partition = success.getProducerRecord().partition();
            // 消息在分区的偏移量
            long offset = success.getRecordMetadata().offset();
            System.out.println("发送消息成功:" + topic + "-" + partition + "-" + offset);
        }, failure -> {
            System.out.println("发送消息失败:" + failure.getMessage());
        });
    }

    @GetMapping("/sendCallback2/{message}")
    public void sendCallback2(@PathVariable String message) {
        logger.info("测试细腻。。。。。。。。。。。,这是往日志信息里面写的东西，会输出到kafka里面");
        kafkaTemplate.send(TOPIC, message).addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {

            @Override
            public void onSuccess(SendResult<Object, Object> result) {
                System.out.println("发送消息成功：" + result.getRecordMetadata().topic() + "-"
                    + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送消息失败：" + ex.getMessage());
            }
        });
        logger.warn("警告信息、、、、、。。。");
    }

}
