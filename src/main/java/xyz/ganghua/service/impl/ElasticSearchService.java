package xyz.ganghua.service.impl;

import java.io.IOException;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.ganghua.configs.ElasticSearchHighLevalConfigure;
import xyz.ganghua.dao.user.User;
import xyz.ganghua.service.IElasticSearchService;
import xyz.ganghua.utils.data.JsonUtil;

@Service
public class ElasticSearchService implements IElasticSearchService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public void createIndex(String indexName) {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        try {
            CreateIndexResponse create =
                client.indices().create(request, ElasticSearchHighLevalConfigure.COMMON_OPTIONS);
            System.out.println(create.isAcknowledged());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getIndex(String indexName) {
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        try {
            GetIndexResponse response =
                client.indices().get(getIndexRequest, ElasticSearchHighLevalConfigure.COMMON_OPTIONS);
            System.out.println(response.getAliases());
            System.out.println(response.getMappings().toString());
            System.out.println(response.getSettings());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertDoc(User user) {
        IndexRequest request = new IndexRequest();
        request.index("user").id(user.getId().toString());
        String json = JsonUtil.toString(user);
        try {
            request.source(json, XContentType.JSON);
            IndexResponse response = client.index(request, ElasticSearchHighLevalConfigure.COMMON_OPTIONS);
            System.out.println(response.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDoc(User user) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("user").id(user.getId().toString());
        updateRequest.doc(XContentType.JSON, "gender", user.getGender());
        UpdateResponse update = client.update(updateRequest, ElasticSearchHighLevalConfigure.COMMON_OPTIONS);
        System.out.println(update.getResult());
    }

    @Override
    public void getDoc() throws IOException {
        GetRequest getRequest = new GetRequest();
        getRequest.index("user").id("123345");
        GetResponse getResponse = client.get(getRequest, ElasticSearchHighLevalConfigure.COMMON_OPTIONS);
        System.out.println(getResponse.getSourceAsString());
    }

    /**
     * 条件查询
     */
    @Override
    public void docQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");
        request.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("gender", "男")));

        SearchResponse response = client.search(request, ElasticSearchHighLevalConfigure.COMMON_OPTIONS);
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }

    }

    /**
     * 批量插入
     */
    @Override
    public void insertDocBatch() throws IOException {

        BulkRequest bulkRequest = new BulkRequest();

        bulkRequest.add(
            new IndexRequest().index("user").id("1001").source(XContentType.JSON, "name", "zhangsan", "gender", "男的"));
        bulkRequest
            .add(new IndexRequest().index("user").id("1002").source(XContentType.JSON, "name", "lisi", "gender", "男的"));
        bulkRequest.add(
            new IndexRequest().index("user").id("1003").source(XContentType.JSON, "name", "wangwu", "gender", "女的"));
        bulkRequest.add(
            new IndexRequest().index("user").id("1001").source(XContentType.JSON, "name", "zhaoliu", "gender", "男的"));
        BulkResponse bulk = client.bulk(bulkRequest, ElasticSearchHighLevalConfigure.COMMON_OPTIONS);
        System.out.println(bulk.getTook());
        System.out.println(bulk.getItems().toString());
    }

    @Override
    public void queryDocAsAll() throws IOException {

        SearchRequest searchRequest = new SearchRequest().indices("user");
        searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));

        SearchResponse search = client.search(searchRequest, ElasticSearchHighLevalConfigure.COMMON_OPTIONS);
        SearchHits hits = search.getHits();
        for (SearchHit searchHit : hits) {
            System.out.println(searchHit.getSourceAsString());
        }
    }

}
