package xyz.ganghua.utils;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import xyz.ganghua.entity.Users;

public class JwtUtil {

    // 有效期 1小时
    public static final Long JWT_TTL = 60 * 60 * 1000L;

    // 设置密钥明文
    public static final String JWT_KEY = "ganghua";

    private static JwtBuilder builder;

    /**
     * uuid
     * 
     * @return
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }

    /**
     * 
     * @param subject
     * @return
     */
    public static String createJWT(Map<String, Object> map) {
        JwtBuilder jwtBuilder = getJwtBuilder(map, null, getUUID());
        return jwtBuilder.compact();
    }

    /**
     * 
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(Map<String, Object> map, Long ttlMillis) {
        JwtBuilder jwtBuilder = getJwtBuilder(map, ttlMillis, getUUID());
        return jwtBuilder.compact();
    }

    /**
     * 
     * @param subject
     * @param ttlMillis
     * @param id
     * @return
     */
    public static String createJWT(Map<String, Object> map, Long ttlMillis, String id) {
        JwtBuilder jwtBuilder = getJwtBuilder(map, ttlMillis, id);
        return jwtBuilder.compact();
    }

    /**
     * 生成一个jwtBuilder对象
     * 
     * @param subject
     * @param ttlMillis
     * @param uuid
     * @return
     */
    private static JwtBuilder getJwtBuilder(Map<String, Object> map, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);
        if (null == ttlMillis) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = currentTimeMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        JwtBuilder builder = Jwts.builder();
        // map.forEach((k, y) -> {
        // builder.claim(k, y);
        // });
        builder.setClaims(map);
        builder.setId(uuid);
        builder.setSubject(JwtUtil.JWT_KEY);
        builder.setIssuedAt(now);
        // 加密方式
        builder.signWith(signatureAlgorithm, secretKey);
        // 设置过期时间
        builder.setExpiration(expDate);
        return builder;

    }

    /**
     * 解析token
     * 
     * @param token
     * @return
     */
    public static Claims parseJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(token).getBody();
        return claims;
    }

    public static SecretKey generalKey() {
        byte[] decodeKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKeySpec key = new SecretKeySpec(decodeKey, 0, decodeKey.length, "AES");
        return key;
    }

    public static void main(String[] args) {
        Map<String, Object> hashMap = new HashMap<>();
        Users users = new Users();
        users.setId(1234L);
        users.setName("lucy");
        users.setPassword("1234");
        hashMap.put("users", users);

        String createJWT = createJWT(hashMap);
        System.out.println(createJWT);

        Claims claims = parseJwt(createJWT);
        System.out.println(claims);
        Users object = claims.get("users", Users.class);
        System.out.println(object);

    }

}
