package com.stmarygate.coral.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class for handling JSON Web Tokens (JWT). Provides methods to generate and verify JWT
 * tokens using RSA256 algorithm.
 *
 * @see java.security.interfaces.RSAPublicKey
 * @see java.security.interfaces.RSAPrivateKey
 */
public class JWTUtils {

  private static final JWTUtils INSTANCE = new JWTUtils();
  private String SECRET = "";

  /**
   * Set the JWT secret to use for generating and verifying tokens.
   *
   * @param secret The secret to use.
   */
  public static JWTUtils setSecret(String secret) {
    INSTANCE.SECRET = secret;
    return INSTANCE;
  }

  /**
   * Generates a JWT token using the provided algorithm, username, and password.
   *
   * @param id The id to include in the token claims.
   * @param email The username to include in the token claims.
   * @return The generated JWT token.
   */
  public String generateToken(String id, String username, String email) {

    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);

    byte[] apiKeySecretBytes = Base64.getDecoder().decode(SECRET);
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, "HmacSHA256");

    JwtBuilder builder =
        Jwts.builder()
            .id(id)
            .claim("email", email)
            .issuedAt(now)
            .subject(username)
            .issuer("Saint Mary Gate")
            .signWith(signingKey);

    // Builds the JWT and serializes it to a compact, URL-safe string
    return builder.compact();
  }

  /**
   * Verifies the authenticity of a JWT token based on the provided algorithm, username, password,
   * and token.
   *
   * @param token The JWT token to verify.
   * @return {@code true} if the token is valid, {@code false} otherwise.
   */
  public boolean verifyToken(String token) {
    try {
      byte[] apiKeySecretBytes = Base64.getDecoder().decode(SECRET);
      SecretKey signingKey = new SecretKeySpec(apiKeySecretBytes, "HmacSHA256");
      Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
