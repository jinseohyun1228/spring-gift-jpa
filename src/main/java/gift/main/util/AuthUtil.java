package gift.main.util;

import gift.main.dto.UserDto;
import gift.main.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class AuthUtil {
    private final SecretKey secretKey;

    public AuthUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String createToken(String name, String email, String password, String role) {
        return Jwts.builder()
                .claim("name", name)
                .claim("email", email)
                .claim("password", password)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) // 토큰 발생시간
                .setExpiration(new Date(System.currentTimeMillis() + 2400000L)) // 소멸시간 셋팅
                .signWith(secretKey) // 시그니처~!
                .compact();
    }

    public String createToken(User user) {
        return Jwts.builder()
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .claim("password", user.getPassword())
                .claim("role", user.getRole())
                .issuedAt(new Date(System.currentTimeMillis())) // 토큰 발생시간
                .setExpiration(new Date(System.currentTimeMillis() + 2400000L)) // 소멸시간 셋팅
                .signWith(secretKey) // 시그니처~!
                .compact();
    }

    public String createToken(UserDto userDto) {
        System.out.println("호출1-5");
        return Jwts.builder()
                .claim("name", userDto.getName())
                .claim("email", userDto.getEmail())
                .claim("password", userDto.getPassword())
                .claim("role", userDto.getRole())
                .issuedAt(new Date(System.currentTimeMillis())) // 토큰 발생시간
                .setExpiration(new Date(System.currentTimeMillis() + 2400000L)) // 소멸시간 셋팅
                .signWith(secretKey) // 시그니처~!
                .compact();

    }

    public boolean validateToken(String token, String email, String role) {
        if (isExpired(token)) {
            return false;
        }

        if (email.equals(getEmail(token))) {
            return false;
        }

        if (role.equals(getRole(token))) {
            return false;
        }
        return true;

    }

    public String getEmail(String token) {
        return Jwts.parser()//파서 생성
                .verifyWith(secretKey)
                .build()//파서 키 설정과 빌드 완료
                .parseSignedClaims(token)//토큰 서명 확인
                .getPayload()
                .get("email", String.class);
    }


    public String getName(String token) {
        return Jwts.parser()//파서 생성
                .verifyWith(secretKey)
                .build()//파서 키 설정과 빌드 완료
                .parseSignedClaims(token)//토큰 서명 확인
                .getPayload()
                .get("name", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload().get("role", String.class);
    }

    private Boolean isExpired(String token) {
        return Jwts.parser()
                .verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration().before(new Date());
    }

}
