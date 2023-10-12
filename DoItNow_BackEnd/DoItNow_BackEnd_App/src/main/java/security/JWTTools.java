package security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import entities.User;
import exceptions.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTTools {

	// * * * * * * * * * * secret * * * * * * * * * *
	@Value("${spring.jwt.secret}")
	private String secret;

	// * * * * * * * * * * create token * * * * * * * * * *
	public String createToken(User user) {
		String token = Jwts.builder().setSubject(user.getId().toString())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
		return token;
	}

	// * * * * * * * * * * verify token * * * * * * * * * *
	public void verifyToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new UnauthorizedException("The token is invalid, please log in again.");
		}
	}

	// * * * * * * * * * * extract subject * * * * * * * * * *
	public String extractSubject(String token) {
		return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token)
				.getBody().getSubject();
	}

}
