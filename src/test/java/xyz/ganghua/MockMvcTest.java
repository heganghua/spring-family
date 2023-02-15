package xyz.ganghua;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import xyz.ganghua.controller.HelloWorldController;
import xyz.ganghua.service.impl.MockBeanService;

// @RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldController.class)
// 测试环境使用，用来表示测试环境使用的ApplicationContext将是WebApplicationContext类型的
// @WebAppConfiguration
@AutoConfigureMockMvc
@TestExecutionListeners(MockitoTestExecutionListener.class)
public class MockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    // @MockBean
    // private HttpServletRequest re;

    @MockBean
    private MockBeanService mk;

    @Before
    public void setup() {
        // 实例化方式一
        mockMvc = MockMvcBuilders.standaloneSetup(new HelloWorldController()).build();
        // 实例化方式二
        // mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testHello() throws Exception {

        // Users users = new Users();
        // users.setName("haha");
        //
        // when(mk.sayHello()).thenReturn("123");
        // String attribute = (String)mk.sayHello();
        // System.out.println(attribute);

        // MockBeanService mc = mock(MockBeanService.class);
        // Mockito.when(mc.sayHello()).thenReturn("zhengkai");
        // System.out.println(mc.sayHello());

        /*
         * 1、mockMvc.perform执行一个请求。
         * 2、MockMvcRequestBuilders.get("XXX")构造一个请求。
         * 3、ResultActions.param添加请求传值
         * 4、ResultActions.accept(MediaType.TEXT_HTML_VALUE))设置返回类型
         * 5、ResultActions.andExpect添加执行完成后的断言。
         * 6、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
         *   比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
         * 7、ResultActions.andReturn表示执行完成后返回相应的结果。
         */
        // mockMvc.perform(MockMvcRequestBuilders.get("/hello")
        // // 设置返回值类型为utf-8，否则默认为ISO-8859-1
        // .accept(MediaType.APPLICATION_JSON_UTF8_VALUE).requestAttr("user", users))
        // .andExpect(MockMvcResultMatchers.status().isOk())
        // .andExpect(MockMvcResultMatchers.content().string("Hello Tom!")).andDo(MockMvcResultHandlers.print());
    }
}
