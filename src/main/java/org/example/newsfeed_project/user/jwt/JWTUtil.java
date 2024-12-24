package org.example.newsfeed_project.user.jwt;

import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

	private SecretKey secretKey;

	//지정해둔 secretKey를 불러와서 새로운 JWT secretKey(객체 키) 생성
	public JWTUtil(@Value("${spring.jwt.secret}")String secret){
		this.secretKey=new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),Jwts.SIG.HS256.key().build().getAlgorithm());
	}

	//토큰의 각 요소 점검
	public String getUserName(String token){

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userName", String.class);
	}

	public String getUserId(String token){

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userId", String.class);
	}

	public String getStatus(String token){

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("status", String.class);
	}

	public Boolean isExpired(String token) {

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
	}

	//토큰 생성 메서드
	public String creatJWT(String userName, Long userId, Boolean status, Long expiredMs){
		return Jwts.builder()
			.claim("userName", userName)
			.claim("userId", userId)
			.claim("status", status)
			.issuedAt(new Date(System.currentTimeMillis()))//토큰 발행 시간
			.expiration(new Date(System.currentTimeMillis()+expiredMs))//토큰 만료 시간
			.signWith(secretKey)//토큰 암호화
			.compact();
	}
}
