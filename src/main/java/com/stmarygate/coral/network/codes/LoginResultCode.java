package com.stmarygate.coral.network.codes;

import lombok.Getter;

/**
 * Login results codes sent by the server to the client.
 */
@Getter
public enum LoginResultCode {

  SUCCESS(1),

  FAILURE_NO_ACCOUNT(2),

  FAILURE_INCORRECT_PASSWORD(3);

  private final int code;

  /**
   * Constructs a new {@code LoginResultCode} with the specified code.
   *
   * @param code The code for the login result.
   */
  LoginResultCode(int code) {
    this.code = code;
  }
}
