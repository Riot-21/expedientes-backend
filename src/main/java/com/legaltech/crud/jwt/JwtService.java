package com.legaltech.crud.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.legaltech.crud.models.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time-ms}")
    private int expirationTimeMs;
    @Value("${security.jwt.issuer}")
    private String issuer;

    // FUNCION PARA CREAR EL JWT
    public String createJwtToken(Usuario user) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        var key = Keys.hmacShaKeyFor(keyBytes);
        return Jwts
                .builder()
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .issuer(issuer)
                .expiration(new Date(System.currentTimeMillis() + expirationTimeMs))
                .signWith(key)
                .compact();
    }

    // FUNCION QUE RETORNA Y VALIDA EL TOKEN
    public Claims getTokenClaims(String token) {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            var key = Keys.hmacShaKeyFor(keyBytes);

            return Jwts.parser()
                    .verifyWith(key) //verifica la firma
                    .build()
                    .parseSignedClaims(token) //verifica el token completo(firma, expiracion, etc)
                    .getPayload(); //devuelve el cuerpo del token

        } catch (ExpiredJwtException e) {
            throw new JwtException("El token ha expirado");
        } catch (SignatureException e) {
            throw new JwtException("Firma JWT no válida");
        } catch (JwtException e) {
            throw new JwtException("Token inválido: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el token: " + e.getMessage());
        }
    }
}
