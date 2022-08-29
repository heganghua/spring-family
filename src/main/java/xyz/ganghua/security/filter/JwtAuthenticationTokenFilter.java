package xyz.ganghua.security.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import xyz.ganghua.utils.JwtUtil;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        // 获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasLength(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 解析token
        Claims claims = JwtUtil.parseJwt(token);
        Map<String, Object> map = (HashMap)claims.get("loginUser");
        System.out.println(map);

        // LoginUser loginUser = JsonUtil.fromJsonString(map.toString(), LoginUser.class);
        System.out.println(map);
        // TODO 权限信息封装进入

        // 构建用户认证token
        if (Objects.nonNull(map)) {
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(map, null, null);
            // 放入安全上下文中
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

}
