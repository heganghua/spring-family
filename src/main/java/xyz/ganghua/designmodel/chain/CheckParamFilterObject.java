package xyz.ganghua.designmodel.chain;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 参数校验
 * 
 * @author ganghua
 * @date 2023/02/15
 */
@Component
@Order(1)
public class CheckParamFilterObject extends AbstractHandler {

    @Override
    void doFilter(Request reqeust, Response response) {
        System.out.println("非空参数检查");
    }

}
