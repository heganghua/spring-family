package xyz.ganghua;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import xyz.ganghua.dao.user.UsersMapper;
import xyz.ganghua.service.IElasticSearchService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTests {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private IElasticSearchService elasticSearchService;

    @Autowired
    private UsersMapper usersMapper;

    @Test
    public void contextLoads() {
        System.out.println(client);
    }

    /**
     * 创建索引
     * 
     * @throws Exception
     */
    @Test
    public void createIndex() throws Exception {
        elasticSearchService.createIndex("user");
    }

    @Test
    public void getIndex() throws Exception {
        elasticSearchService.getIndex("user");
    }

    @Test
    public void insertDoc() throws Exception {
        // User user = new User();
        // user.setId(123345L);
        // user.setName("Lucy");
        // user.setGender("gay");
        // user.setAddr("Beijing");
        // elasticSearchService.insertDoc(user);
    }

    @Test
    public void updateDoc() throws Exception {
        // User user = new User();
        // user.setId(123345L);
        // user.setGender("女");
        // elasticSearchService.updateDoc(user);
    }

    @Test
    public void getDoc() throws Exception {
        elasticSearchService.getDoc();
    }

    @Test
    public void docQuery() throws Exception {
        elasticSearchService.docQuery();
    }

    @Test
    public void insertDocBatch() throws Exception {
        elasticSearchService.insertDocBatch();
    }

    @Test
    public void queryDocAsAll() throws Exception {
        elasticSearchService.queryDocAsAll();
    }

    @Test
    public void saveIndex() throws Exception {
        // IndexRequest indexRequest = new IndexRequest();
        // indexRequest.id("12308");
        // indexRequest.index("haha");
        // User user = new User();
        // user.setId(12306L);
        // user.setName("Lili");
        // user.setGender("gay");
        // user.setAddr("Beijing");
        //
        // // 用jackson中的对象转json数据
        // ObjectMapper objectMapper = new ObjectMapper();
        // String json = objectMapper.writeValueAsString(user);
        // indexRequest.source(json, XContentType.JSON);
        // 执行
        // IndexResponse index = client.index(indexRequest, ElasticSearchHighLevalConfigure.COMMON_OPTIONS);
        // System.out.println(index);
    }

    @Test
    public void searchAll() throws Exception {
        // SearchRequest searchRequest = new SearchRequest();
        // searchRequest.indices("haha");
        // SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // searchRequest.source(searchSourceBuilder);
        // SearchResponse response = client.search(searchRequest, ElasticSearchHighLevalConfigure.COMMON_OPTIONS);
        // System.out.println(response);
    }

}
