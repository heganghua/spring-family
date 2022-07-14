package xyz.ganghua.security.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import xyz.ganghua.dao.user.UsersMapper;
import xyz.ganghua.dto.LoginUser;
import xyz.ganghua.entity.Users;

@Service
public class UsersDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users users = usersMapper.selectOne(new LambdaQueryWrapper<Users>().eq(Users::getName, username));
        if (Objects.isNull(users)) {
            throw new RuntimeException("找不到用户");
        }

        return new LoginUser(users);
    }

}
