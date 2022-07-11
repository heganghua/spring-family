package xyz.ganghua;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import xyz.ganghua.dao.user.UsersMapper;
import xyz.ganghua.entity.Users;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void userMapperTest() {
        Users selectOne = usersMapper.selectOne(new LambdaQueryWrapper<Users>().eq(Users::getName, "lisi"));
        System.out.println(selectOne);
    }

    @Test
    public void passwordTest() throws Exception {
        String encode = passwordEncoder.encode("123");
        System.out.println(encode);

    }

}
