package xyz.ganghua.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.ganghua.dto.ResponseResult;
import xyz.ganghua.entity.Database;
import xyz.ganghua.entity.Users;
import xyz.ganghua.service.ILoginService;

@RestController
public class LoginController {

    @Autowired
    private ILoginService loginservice;

    @Value("${server.port}")
    private String name;

    @Autowired
    private Database db;

    @PostMapping("user/login")
    public ResponseResult<Object> login(@RequestBody Users user) {
        return loginservice.login(user);
    }

    @GetMapping("/hello")
    public ResponseResult<Database> hello() {
        ResponseResult<Database> ok = ResponseResult.ok(db);
        return ok;
    }

}
