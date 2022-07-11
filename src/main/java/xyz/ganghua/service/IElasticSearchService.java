package xyz.ganghua.service;

import java.io.IOException;

import xyz.ganghua.entity.Users;

public interface IElasticSearchService {

    void createIndex(String indexName);

    void getIndex(String indexName);

    void insertDoc(Users users);

    void updateDoc(Users users) throws IOException;

    void getDoc() throws IOException;

}
