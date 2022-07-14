package xyz.ganghua.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.ganghua.dto.ResponseResult;
import xyz.ganghua.entity.Users;
import xyz.ganghua.service.ILoginService;

@RestController
public class LoginController {

    @Autowired
    private ILoginService loginservice;

    @PostMapping("user/login")
    public ResponseResult<Object> login(@RequestBody Users user) {
        return loginservice.login(user);
    }

}
