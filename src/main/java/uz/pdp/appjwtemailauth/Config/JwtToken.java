package uz.pdp.appjwtemailauth.Config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.appjwtemailauth.Entity.Role;

import java.util.Date;
import java.util.Set;

@Component

public class JwtToken {
    private static final long expritime = 100 * 24 * 60 * 90;
    private static final String maxfiysoztoken = "AssalomualaykumXurmatliJavabackendkamsablar12345";

    public String genereteToken(String username, Set<Role> roles) {
        Date expiredate = new Date(System.currentTimeMillis() + expritime);

        String compact = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiredate)
                .claim("role", roles)
                .signWith(SignatureAlgorithm.HS512, maxfiysoztoken)
                .compact();
        return compact;
    }

    public String getusernameToken(String token) {
        try {
            String subject = Jwts
                    .parser()
                    .setSigningKey(maxfiysoztoken)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return subject;
        } catch (Exception e) {
            return null;

        }
    }
}
