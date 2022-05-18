package xyz.ganghua.configs;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RequestOptions.Builder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchHighLevalConfigure {

    // 协议
    @Value("${elasticsearch.schema:http}")
    private String schema = "http";

    // 集群地址
    @Value("${elasticsearch.address}")
    private String address;

    @Value("${elasticsearch.connectTimeout:600000}")
    private int connectTimeout;

    @Value("${elasticsearch.socketTimeout:600000}")
    private int socketTimeout;

    @Value("${elasticsearch.connectionRequestTimeout:10000}")
    private int connectionRequestTimeout;

    @Value("${elasticsearch.maxConnectNum:100}")
    private int maxConnectNum;

    @Value("${elasticsearch.maxConnectPerRoute:100}")
    private int maxConnectPerRoute;

    public static final RequestOptions COMMON_OPTIONS;

    static {
        Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient restHighLevelClient() {

        List<HttpHost> hostLists = new ArrayList<>();
        String[] hostList = address.split(",");
        for (String addr : hostList) {
            String host = addr.split(":")[0];
            String port = addr.split(":")[1];
            hostLists.add(new HttpHost(host, Integer.parseInt(port), schema));
        }

        HttpHost[] httpHost = hostLists.toArray(new HttpHost[] {});
        // 构建一个连接对象
        RestClientBuilder builder = RestClient.builder(httpHost);
        // 异步连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(connectTimeout);
            requestConfigBuilder.setSocketTimeout(socketTimeout);
            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeout);

            return requestConfigBuilder;
        });
        // 异步连接数配置
        builder.setHttpClientConfigCallback(httpClientConfigCallback -> {
            httpClientConfigCallback.setMaxConnTotal(maxConnectNum);
            httpClientConfigCallback.setMaxConnPerRoute(maxConnectPerRoute);
            httpClientConfigCallback.setKeepAliveStrategy((response, context) -> Duration.ofMinutes(5).toMillis());
            return httpClientConfigCallback;
        });

        return new RestHighLevelClient(builder);
    }
}
