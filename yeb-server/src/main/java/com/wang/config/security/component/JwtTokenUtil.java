package com.wang.config.security.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//jwt：token工具类
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME="sub";//用户名的key
    private static final String CLAIM_KEY_CREATED="created";//jwt的创建时间
    @Value("${jwt.secret}")//通过刚在在yaml中配置的属性拿到
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

//    根据用户信息生成token令牌

    public String generateToken(UserDetails userDetails){
//        准备荷载
        Map<String,Object> claims=new HashMap<>();
//        荷载的username
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
//        创建时间：当前时间
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

//    根据荷载生成jwt token..
    private String generateToken(Map<String,Object> claims){

        return Jwts.builder()
//                荷载
                .setClaims(claims)
//                失效时间：准备generateExpirationDate()方法
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret)
//                生成token
                .compact();
    }
    //失效时间
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis()+expiration*1000);
    }

//    从token中获取登录用户名
public String getUserNameFromToken(String token){
        String username;
        try {//得到getClaimsFormToken返回的解析claims
            Claims claims = getClaimsFormToken(token);
            username = claims.getSubject();
        }catch (Exception e){
            username=null;
        }
        return username;
    }

    private Claims getClaimsFormToken(String token) {
        Claims claims=null;
        try {//拿到荷载
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return claims;
    }



//    验证token是否有效，判断userDetails里面的username是否与token解析出来的username相等，并判断token是否失效
    public boolean validateToken(String token,UserDetails userDetails){
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {

        Date expireDate=getExpiredDateFromToken(token);
        return expireDate.before(new Date());
    }
    //获取失效时间
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return claims.getExpiration();
    }


//判断token是否可以被刷新
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }
//刷新token
    public String refreshToken(String token){
        Claims claims=getClaimsFormToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }






}
