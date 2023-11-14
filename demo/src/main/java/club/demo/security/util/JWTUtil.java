package club.demo.security.util;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
public class JWTUtil {

    private String secretKey = "zerock12345678";

    //1month
    private long expire = 60 * 24 * 30;

    public String generateToken(String content) throws Exception{
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().plusSeconds(1).toInstant()))
                .claim("sub",content)
                .signWith(SignatureAlgorithm.HS512,secretKey.getBytes("UTF-8"))
                .compact();
    }

    public String validateAndExtract(String tokenStr) throws Exception{
        String contentValue = null;
        try {
            DefaultJws defaultJws = (DefaultJws) Jwts.parser()
                    .setSigningKey(secretKey.getBytes("UTF-8"))
                    .parseClaimsJws(tokenStr);
            log.info("{}",defaultJws);

            log.info("{}", defaultJws.getBody().getClass());

            DefaultClaims claims = (DefaultClaims) defaultJws.getBody();

            log.info("----------------------------------------------");

            contentValue = claims.getSubject();
        } catch (Exception e){
            e.printStackTrace();
            log.error("{}",e.getMessage());
            contentValue = null;
        }
        return contentValue;
    }
}
