package com.stmarygate.coral.utils;

import java.security.SecureRandom;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility class for encrypting and checking passwords using BCrypt.
 *
 * <p>This class uses the {@link BCryptPasswordEncoder}.
 *
 * @see org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
 * @see java.security.SecureRandom
 */
public class BCryptEncryptionUtils {

  /** The BCrypt password encoder. */
  private static BCryptPasswordEncoder bCryptPasswordEncoder = null;

  /**
   * Encrypts the given password using BCrypt.
   *
   * @param secureRandom The secure random number generator.
   * @param password The password to encrypt.
   * @return The BCrypt-encrypted password.
   */
  public static String encrypt(SecureRandom secureRandom, String password) {
    init(secureRandom);
    return bCryptPasswordEncoder.encode(password);
  }

  /**
   * Checks if the provided password matches the BCrypt hash.
   *
   * @param secureRandom The secure random number generator.
   * @param password The password to check.
   * @param hash The BCrypt hash to compare against.
   * @return {@code true} if the password matches the hash, {@code false} otherwise.
   */
  public static boolean check(SecureRandom secureRandom, String password, String hash) {
    init(secureRandom);
    return bCryptPasswordEncoder.matches(password, hash);
  }

  /**
   * Initializes the BCrypt password encoder if not already initialized.
   *
   * @param secureRandom The secure random number generator.
   */
  private static void init(SecureRandom secureRandom) {
    if (bCryptPasswordEncoder == null) {
      bCryptPasswordEncoder = new BCryptPasswordEncoder(16, secureRandom);
    }
  }
}
