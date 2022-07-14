package xyz.ganghua.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import xyz.ganghua.dto.LoginUser;
import xyz.ganghua.dto.ResponseResult;
import xyz.ganghua.entity.Users;
import xyz.ganghua.service.ILoginService;
import xyz.ganghua.utils.JwtUtil;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseResult<Object> login(Users users) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(users.getName(), users.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }

        // 登录成功
        LoginUser principal = (LoginUser)authenticate.getPrincipal();
        Users usersForSQL = principal.getUsers();
        Map<String, Object> map = new HashMap<>();
        map.put("users", usersForSQL);

        String jwt = JwtUtil.createJWT(map);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("token", jwt);
        ResponseResult<Object> ok = ResponseResult.ok(responseMap);
        return ok;
    }

}
