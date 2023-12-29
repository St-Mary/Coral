package com.stmarygate.coral.utils;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * This class contains utility methods for creating and initializing SSL contexts.
 *
 * <p>SSLContextUtils use {@link KeyStore} to create and initialize SSL contexts, which are used to
 * create a secure connection between a client and a server. This {@link SSLContext} is used to
 * create a {@link javax.net.ssl.SSLEngine} which is used to create a {@link
 * javax.net.ssl.SSLSession} which is us.
 *
 * <p><strong>Usage:</strong> To use this class, call {@link
 * #createAndInitSSLContext(FileInputStream, String)} with the JKS file and the store password.
 */
public class SSLContextUtils {

  /**
   * Creates and initializes an SSL context using the specified JKS file and store password.
   *
   * @param jksFile The JKS file.
   * @param storePass The store password for the JKS file.
   * @return The initialized SSL context that can be used to create a secure connection.
   * @throws Exception If an exception occurs during the initialization process.
   */
  public static SSLContext createAndInitSSLContext(FileInputStream jksFile, String storePass)
      throws Exception {
    KeyStore trustStore = KeyStore.getInstance("jks");
    trustStore.load(jksFile, storePass.toCharArray());

    KeyManagerFactory keyManagerFactory =
        KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    keyManagerFactory.init(trustStore, storePass.toCharArray());

    TrustManagerFactory trustManagerFactory =
        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    trustManagerFactory.init(trustStore);

    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(
        keyManagerFactory.getKeyManagers(),
        trustManagerFactory.getTrustManagers(),
        new SecureRandom());

    return sslContext;
  }
}
