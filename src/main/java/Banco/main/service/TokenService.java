package Banco.main.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import Banco.main.model.Usuario;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String chave;

	public String generateToken(Usuario user) {
		try {
			System.out.println(user.getUsername());
			Algorithm algoritmo = Algorithm.HMAC256(chave);
			return JWT.create().withIssuer("TokenGerenciador")
					.withSubject(user.getUsername())
					.withExpiresAt(ExpirationToken())
					.sign(algoritmo);
		} catch (JWTCreationException e) {
			throw new RuntimeException("Erro ao gerar o token", e);
		}
	}

	public String validarToken(String token) {
		try {
			Algorithm algoritmo = Algorithm.HMAC256(chave);
			return JWT.require(algoritmo).withIssuer("TokenGerenciador")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			System.out.println("Ocorreu exception em validar o token");
			return "";
		}
	}

	public Instant ExpirationToken() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-04:00"));
	}
}
