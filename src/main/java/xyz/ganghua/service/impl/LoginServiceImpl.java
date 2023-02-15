package xyz.ganghua.service.impl;

import org.springframework.stereotype.Service;

import xyz.ganghua.dto.ResponseResult;
import xyz.ganghua.entity.Users;
import xyz.ganghua.service.ILoginService;

@Service
public class LoginServiceImpl implements ILoginService {

    @Override
    public ResponseResult<Object> login(Users users) {
        // TODO Auto-generated method stub
        return null;
    }

    // @Autowired
    // private AuthenticationManager authenticationManager;
    //
    // @Override
    // public ResponseResult<Object> login(Users users) {
    //
    // Authentication authentication = new UsernamePasswordAuthenticationToken(users.getName(), users.getPassword());
    // Authentication authenticate = authenticationManager.authenticate(authentication);
    // if (Objects.isNull(authenticate)) {
    // throw new RuntimeException("登录失败");
    // }
    //
    // // 登录成功
    // LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
    // // Users usersForSQL = principal.getUsers();
    // Map<String, Object> map = new HashMap<>();
    // map.put("loginUser", loginUser);
    //
    // String jwt = JwtUtil.createJWT(map);
    //
    // Map<String, String> responseMap = new HashMap<>();
    // responseMap.put("token", jwt);
    // ResponseResult<Object> ok = ResponseResult.ok(responseMap);
    // return ok;
    // }
    //
    // public static void main(String[] args) {
    // ClassLoader classLoader = LoginServiceImpl.class.getClassLoader();
    // try {
    // Class<?> clazz = classLoader.loadClass("xyz.ganghua.service.impl.LoginServiceImpl");
    // String simpleName = clazz.getSimpleName();
    // String decapitalize = Introspector.decapitalize(simpleName);
    // System.out.println(decapitalize);
    //
    // } catch (ClassNotFoundException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    // }

}
