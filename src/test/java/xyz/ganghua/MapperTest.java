package xyz.ganghua;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.ganghua.dao.consumer.ConsumerMapper;
import xyz.ganghua.dao.user.UsersMapper;
import xyz.ganghua.entity.Consumer;
import xyz.ganghua.entity.Users;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

    @Autowired
    private UsersMapper userMapper;

    @Autowired
    private ConsumerMapper consumerMapper;

//    MapperTest(UsersMapper userMapper, ConsumerMapper consumerMapper){
//        this.userMapper = userMapper;
//        this.consumerMapper = consumerMapper;
//    }

     @Test
     public void consumerMapperTest(){
         List<Consumer> haha = consumerMapper.selectList(new LambdaQueryWrapper<Consumer>().eq(Consumer::getName, "haha"));
         haha.stream().forEach(item -> System.out.println(item.toString()));
     }


     @Test
     public void userMapperTest() {
     Users selectOne = userMapper.selectOne(new LambdaQueryWrapper<Users>().eq(Users::getName, "lisi"));
     System.out.println(selectOne);
     }

    @Test
    public void githubUlisesbocchioText() throws Exception {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("qwera@12345");
        // 加密数据库连接地址
        // System.out.println(encryptor.encrypt("jdbc:mysql://localhost:3306/datacenter?useSSL=false"));
        // 加密数据库连接用户名
        System.out.println(encryptor.encrypt("root"));
        // 加密数据库连接密码
        System.out.println(encryptor.encrypt("123456"));
        // 加密ip地址
        System.out.println(encryptor.encrypt("localhost"));
        // 端口号
        System.out.println(encryptor.encrypt("3306"));
        // 数据库
        System.out.println(encryptor.encrypt("datacenter"));

    }

}
