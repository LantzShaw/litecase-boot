package com.litecase.boot.web.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;


/**
 * 参考文章:
 * https://blog.csdn.net/qq_50969362/article/details/134100542
 * https://jwt.io/
 */
@Component
public class JwtUtil {
    /**
     * 过期时间(单位:秒)
     */
    private static final long ACCESS_EXPIRE = 60 * 60 * 1000; // 1 hour

    private static  final SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;

    /**
     * 私钥 / 生成签名的时候使用的秘钥secret，一般可以从本地配置文件中读取，切记这个秘钥不能外露，只在服务端使用，在任何场景都不应该流露出去。
     * 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
     * 应该大于等于 256位(长度32及以上的字符串)，并且是随机的字符串
     */
    private  static final String SECRET = "secret";

    /**
     * 秘钥实例
     */
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    /**
     * jwt主题
     */
    private static final  String JWT_SUBJECT = "subject";

    /**
     * jwt签发者
     */
    private static final   String JWT_ISSUER = "issuer";

    public String createToken(String username) {
        String uuid = UUID.randomUUID().toString();
        Date exprireDate = Date.from(Instant.now().plusSeconds(ACCESS_EXPIRE));

        // Date exprireDate = new Date(now.getTime() + ACCESS_EXPIRE);

        return Jwts.builder().header().add("typ","JWT").add("alg","HS256").and().claim("username", username)
                .id(uuid)
                .expiration(exprireDate)
                .issuedAt(new Date())
                .subject(JWT_SUBJECT)
                .issuer(JWT_ISSUER)
                .signWith(SECRET_KEY, ALGORITHM)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
