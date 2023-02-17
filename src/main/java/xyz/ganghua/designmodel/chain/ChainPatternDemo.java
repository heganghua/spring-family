package xyz.ganghua.designmodel.chain;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 责任链模式使用
 * 
 * @author ganghua
 * @date 2023/02/15
 */
@Component("ChainPatternDemo")
public class ChainPatternDemo {

    // 自动注入各个责任链的对象
    @Autowired
    private List<AbstractHandler> abstractHandlerList;

    private AbstractHandler abstractHandler;

    // Spring注入后自动执行，责任链的对象连接起来
    // 只循环一次时， nextHandler是没有值的，当i > 0时 设置责任链的nextHandler
    @PostConstruct
    public void initializeChainFilter() {
        for (int i = 0; i < abstractHandlerList.size(); i++) {
            if (0 == i) {
                abstractHandler = abstractHandlerList.get(0);
            } else {
                AbstractHandler currentHander = abstractHandlerList.get(i - 1);
                AbstractHandler nextHandler = abstractHandlerList.get(i);
                currentHander.setNextHandler(nextHandler);
            }
        }
    }

    public Response exec(Request request, Response response) {
        abstractHandler.filter(request, response);
        return response;
    }

    public AbstractHandler getAbstractHandler() {
        return abstractHandler;
    }

    public void setAbstractHandler(AbstractHandler abstractHandler) {
        this.abstractHandler = abstractHandler;
    }

}
