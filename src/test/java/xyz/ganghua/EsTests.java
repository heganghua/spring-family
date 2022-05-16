package xyz.ganghua;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.ganghua.configs.ElasticSearchConfiguration;
import xyz.ganghua.dao.user.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTests {

    @Autowired
    private RestHighLevelClient client;

    @Test
    public void contextLoads() {
        System.out.println(client);
    }

    @Test
    public void saveIndex() throws Exception {
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.id("12308");
        indexRequest.index("haha");
        User user = new User();
        user.setId(12306L);
        user.setName("Lili");
        user.setGender("gay");
        user.setAddr("Beijing");

        // 用jackson中的对象转json数据
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        indexRequest.source(json, XContentType.JSON);
        // 执行
        IndexResponse index = client.index(indexRequest, ElasticSearchConfiguration.COMMON_OPTIONS);
        System.out.println(index);
    }

    @Test
    public void searchAll() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("haha");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, ElasticSearchConfiguration.COMMON_OPTIONS);
        System.out.println(response);
    }

}
