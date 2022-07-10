package xyz.ganghua.service;

import java.io.IOException;

import xyz.ganghua.dao.user.User;

public interface IElasticSearchService {

    void createIndex(String indexName);

    void getIndex(String indexName);

    void insertDoc(User user);

    void updateDoc(User user) throws IOException;

    void getDoc() throws IOException;

    void docQuery() throws IOException;

    void insertDocBatch() throws IOException;

    void queryDocAsAll() throws IOException;

}
