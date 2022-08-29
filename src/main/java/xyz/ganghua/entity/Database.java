package xyz.ganghua.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "database")
public class Database {
    private String username;
    private String password;
    private String host;
    private String port;
    private String dbname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public Database() {
        super();
    }

    public Database(String username, String password, String host, String port, String dbname) {
        super();
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.dbname = dbname;
    }
}
