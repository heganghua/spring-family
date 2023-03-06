package xyz.ganghua;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@EmbeddedKafka(count = 4, ports = {9092, 9093, 9094, 9095})
public class KafkaApplication {

    @Test
    public void contextLoads() throws IOException {
        System.in.read();
    }
}
