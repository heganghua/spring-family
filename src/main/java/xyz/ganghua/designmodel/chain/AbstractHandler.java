package xyz.ganghua.designmodel.chain;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;

/**
 * 责任链模式
 * 
 * @author ganghua
 * @date 2023/02/15
 */
public abstract class AbstractHandler {

    private AbstractHandler nextHandler;

    public void setNextHandler(AbstractHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public AbstractHandler getNextHandler() {
        return nextHandler;
    }

    public void filter(Request request, Response response) {
        doFilter(request, response);
        if (getNextHandler() != null) {
            getNextHandler().filter(request, response);
        }
    }

    abstract void doFilter(Request reqeust, Response response);
}
