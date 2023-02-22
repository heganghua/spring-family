package xyz.ganghua.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaTestController {

    private final static String TOPIC = "TOPIC_INPUT";
    private final Logger logger = LoggerFactory.getLogger(KafkaTestController.class);

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @GetMapping("/send/{input}")
    public void sendFoo(@PathVariable String input) {
        kafkaTemplate.send(TOPIC, input);
    }

    @KafkaListener(id = "webGroup", topics = TOPIC)
    public void listen(String input) {
        logger.info("input value: {}", input);
    }

    @GetMapping("/kafka/sendCallback/{message}")
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

    @GetMapping("/kafka/sendCallback2/{message}")
    public void sendCallback2(@PathVariable String message) {
        kafkaTemplate.send("topic1", message).addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {

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
    }

}
