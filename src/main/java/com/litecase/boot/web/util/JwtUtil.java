package com.litecase.boot.web.util;

import com.litecase.boot.web.model.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;


/**
 * 参考文章:
 * https://blog.csdn.net/qq_50969362/article/details/134100542
 * https://jwt.io/
 */
@Slf4j
@Component
public class JwtUtil {
    /**
     * 过期时间(单位:秒)
     */
    private static final long ACCESS_EXPIRE = 60 * 60 * 1000; // 1 hour

    private static final SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;

    /**
     * 私钥 / 生成签名的时候使用的秘钥secret，一般可以从本地配置文件中读取，切记这个秘钥不能外露，只在服务端使用，在任何场景都不应该流露出去。
     * 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
     * 应该大于等于 256位(长度32及以上的字符串)，并且是随机的字符串
     * <p>
     * your key string should be at least 32 characters long:
     */
    private static final String SECRET = "my_secret_name-timeaasd=12304123412341asfdjaskdlf";

    /**
     * 秘钥实例
     */
//    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * jwt主题
     */
    private static final String JWT_SUBJECT = "subject";

    /**
     * jwt签发者
     */
    private static final String JWT_ISSUER = "issuer";

    public String generateToken(String username) {
        String uuid = UUID.randomUUID().toString();
        Date exprireDate = Date.from(Instant.now().plusSeconds(ACCESS_EXPIRE));

        // Date exprireDate = new Date(now.getTime() + ACCESS_EXPIRE);

        return Jwts.builder().header().add("typ", "JWT").add("alg", "HS256").and().claim("username", username)
                .id(uuid)
                .expiration(exprireDate)
                .issuedAt(new Date())
                .subject(JWT_SUBJECT)
                .issuer(JWT_ISSUER)
                .signWith(SECRET_KEY, ALGORITHM)
                .compact();
    }

    /**
     * 从 token 中解析出 Claims（声明）
     *
     * @param token
     * @return
     */
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
//        return Jwts.parser()
//                .setSignWith(SECRET_KEY, ALGORITHM) // 设置用于解析的密钥
//                .parseClaimsJws(token) // 解析 token
//                .getBody(); // 获取声明体（Claims）
    }


    /**
     * 验证Token是否有效
     *
     * @param token
     * @param username
     * @return
     */
//    public  boolean validateToken(String token, String username) {
//        try {
//            String tokenUsername = extractUsername(token);
//
//            if(!username.equals(tokenUsername)) {
//                return false;
//            }
//
//            return !isTokenExpired(token);
//
//            // return extractUsername(token).equals(username);
//            // return (username.equals(tokenUsername) && !isTokenExpired(token));
//
//        } catch (SignatureException | ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
//            return false;
//        }
//    }

    /**
     * 验证Token是否有效
     *
     * @param token
     * @param username
     * @return
     */
    public boolean validateToken(String token, String username) {
        try {
            String tokenUsername = extractUsername(token);

            if (!username.equals(tokenUsername)) {
                return false;
            }

            return !isTokenExpired(token);
        } catch (SignatureException e) {
            // 签名不匹配或无效
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            // JWT格式不正确
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            // JWT已过期
            log.info("Expired JWT token.");
        } catch (IllegalArgumentException e) {
            // JWT为空或为null
            log.info("JWT claims string is empty.");
        } catch (UnsupportedJwtException e) {
            // JWT令牌不支持
            log.info("JWT token is unsupported.");
        }

        return false;
    }

    /**
     * 验证Token是否过期
     *
     * @param token
     * @return
     */
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    /**
     * 提取Token中的用户名
     *
     * @param token
     * @return
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * 提取Token中的用户名
     *
     * @param token
     * @return
     */
//    public String getUsernameFromToken(String token) {
//        return extractClaims(token).getSubject();
//    }
    public String getAuthentication(String token) {
        String tokenUsername = extractUsername(token);

        User principal = new User();

        // 构造一个 User 对象（通常这里可以使用从数据库中查询到的用户信息）
        // User principal = new User(username, "", Collections.emptyList());

        return "";
        // 创建 Authentication 对象，传递用户和权限信息
        // return UsernamePasswordAuthenticationToken(principal, token, "");
    }

//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
//        }
//    }

//    public String getUsername(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
//    }
}

//package com.xxx.config;
//
//        import com.xxx.util.FileProcessUtil;
//        import com.xxx.util.MD5Util;
//        import io.jsonwebtoken.Claims;
//        import io.jsonwebtoken.Jwts;
//        import io.jsonwebtoken.security.Keys;
//        import org.springframework.beans.factory.annotation.Value;
//        import org.springframework.security.core.userdetails.UserDetails;
//        import org.springframework.stereotype.Component;
//
//        import javax.crypto.SecretKey;
//        import java.util.Date;
//        import java.util.HashMap;
//        import java.util.Map;
//        import java.util.UUID;
//
///**
// * @Author jx
// * @Date 2023/11/27
// * @Version 1.0
// * @Description: jwt工具类，JSON WEB TOKEN，分别由：Header（头部）、Payload（负载）、Signature（签名）组成
// */
//@Component
//public class JXJwtTokenUtil
//{
//    //过期时间
//    @Value("${jwt.expiration}")
//    private Long expiration;
//
//    @Value("${jwt.salt}")
//    private String salt;
//
//    /**
//     * 1.从本地文件读取秘钥
//     * 2.工程.yml中配置salt
//     * 3.最终生成jwt秘钥，秘钥组成：MD5（1+2）
//     */
//    private SecretKey secret = Keys.hmacShaKeyFor(MD5Util.generatorSecret(FileProcessUtil.readLocalFileForSecret()+salt).getBytes());
//
//
//    /**
//     * 初始化负载内数据
//     * @param username 用户名
//     * @return 负载集合
//     */
//    private Map<String,Object> initClaims(String username){
//        Map<String, Object> claims = new HashMap<>();
//        //"iss" (Issuer): 代表 JWT 的签发者。在这个字段中填入一个字符串，表示该 JWT 是由谁签发的。例如，可以填入你的应用程序的名称或标识符。
//        claims.put("iss","jx");
//        //"sub" (Subject): 代表 JWT 的主题，即该 JWT 所面向的用户。可以是用户的唯一标识符或者其他相关信息。
//        claims.put("sub",username);
//        //"exp" (Expiration Time): 代表 JWT 的过期时间。通常以 UNIX 时间戳表示，表示在这个时间之后该 JWT 将会过期。建议设定一个未来的时间点以保证 JWT 的有效性，比如一个小时、一天、一个月后的时间。
//        claims.put("exp",generatorExpirationDate());
//        //"aud" (Audience): 代表 JWT 的接收者。这个字段可以填入该 JWT 预期的接收者，可以是单个用户、一组用户、或者某个服务。
//        claims.put("aud","internal use");
//        //"iat" (Issued At): 代表 JWT 的签发时间。同样使用 UNIX 时间戳表示。
//        claims.put("iat",new Date());
//        //"jti" (JWT ID): JWT 的唯一标识符。这个字段可以用来标识 JWT 的唯一性，避免重放攻击等问题。
//        claims.put("jti",UUID.randomUUID().toString());
//        //"nbf" (Not Before): 代表 JWT 的生效时间。在这个时间之前 JWT 不会生效，通常也是一个 UNIX 时间戳。我这里不填，没这个需求
//        return claims;
//    }
//
//    /**
//     * 根据用户信息生成token
//     *
//     * @param userDetails 用户信息
//     * @return token
//     */
//    public String generatorToken(UserDetails userDetails)
//    {
//        Map<String, Object> claims = initClaims(userDetails.getUsername());
//        return generatorToken(claims);
//    }
//
//    /**
//     * 根据负载生成JWT token
//     * @param claims 负载
//     * @return token
//     */
//    private String generatorToken(Map<String,Object> claims){
//        return Jwts.builder()
//                .claims(claims)
//                .signWith(secret,Jwts.SIG.HS256)
//                .compact();
//    }
//
//    /**
//     * 生成失效时间，以秒为单位
//     *
//     * @return 预计失效时间
//     */
//    private Date generatorExpirationDate()
//    {
//        //预计失效时间为：token生成时间+预设期间
//        return new Date(System.currentTimeMillis() + expiration * 1000);
//    }
//
//    /**
//     * 从Token中获取用户名
//     * @param token token
//     * @return 用户名
//     */
//    public String getUserNameFromToken(String token){
//        String username;
//        try
//        {
//            username = getPayloadFromToken(token).getSubject();
//        }catch (Exception e){
//            username = null;
//        }
//        return username;
//    }
//
//    /**
//     * 从Token中获取负载中的Claims
//     * @param token token
//     * @return 负载
//     */
//    private Claims getPayloadFromToken(String token)
//    {
//        return Jwts.parser()
//                .verifyWith(secret)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//
//    /**
//     * 验证token是否有效
//     * @param token 需要被验证的token
//     * @param userDetails true/false
//     * @return
//     */
//    public boolean validateToken(String token,UserDetails userDetails){
//        return getUserNameFromToken(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
//    }
//
//    /**
//     * 判断token是否有过期
//     * @param token 需要被验证的token
//     * @return true/false
//     */
//    private boolean isTokenExpired(String token)
//    {
//        //判断预设时间是否在当前时间之前，如果在当前时间之前，就表示过期了，会返回true
//        return getExpiredDateFromToken(token).before(new Date());
//    }
//
//    /**
//     * 从token中获取预设的过期时间
//     * @param token token
//     * @return 预设的过期时间
//     */
//    private Date getExpiredDateFromToken(String token)
//    {
//        return getPayloadFromToken(token).getExpiration();
//    }
//
//    /**
//     * 判断token是否可以被刷新
//     * @param token 需要被验证的token
//     * @return true/false
//     */
//    public boolean canRefresh(String token){
//        return !isTokenExpired(token);
//    }
//
//    /**
//     * 刷新token
//     * @param token 需要被刷新的token
//     * @return 刷新后的token
//     */
//    public String refreshToken(String token){
//        Claims claims = getPayloadFromToken(token);
//        Map<String, Object> initClaims = initClaims(claims.getSubject());
//        initClaims.put("iat",new Date());
//        return generatorToken(initClaims);
//    }
//}


