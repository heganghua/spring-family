package xyz.ganghua.security.service;

import org.springframework.stereotype.Service;

@Service
public class UsersDetailsServiceImpl /*implements UserDetailsService */ {

    // @Autowired
    // private UsersMapper usersMapper;
    //
    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //
    // Users users = usersMapper.selectOne(new LambdaQueryWrapper<Users>().eq(Users::getName, username));
    // if (Objects.isNull(users)) {
    // throw new RuntimeException("找不到用户");
    // }
    //
    // return new LoginUser(users);
    // }

}
