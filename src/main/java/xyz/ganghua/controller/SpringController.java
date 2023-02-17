package xyz.ganghua.controller;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.ganghua.designmodel.chain.ChainPatternDemo;
import xyz.ganghua.service.impl.MockBeanService;
import xyz.ganghua.spring.SpringContextUtil;

@RestController
@RequestMapping("/spring")
public class SpringController {

    @Autowired
    private ChainPatternDemo chainPatternDemo;

    @GetMapping("/aca")
    public String getMockBeanService() {
        MockBeanService bean = SpringContextUtil.getBean(MockBeanService.class);
        return bean.sayHello();
    }

    @GetMapping("/design")
    public String designPattern() {
        Request request = new Request(null);
        Response response = new Response();
        chainPatternDemo.exec(request, response);
        return null;
    }
}
