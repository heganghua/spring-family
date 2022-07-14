package xyz.ganghua.service;

import xyz.ganghua.dto.ResponseResult;
import xyz.ganghua.entity.Users;

public interface ILoginService {
    ResponseResult<Object> login(Users users);
}
