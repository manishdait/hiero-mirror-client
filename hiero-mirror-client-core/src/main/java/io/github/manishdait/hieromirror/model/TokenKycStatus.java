package io.github.manishdait.hieromirror.model;

public enum TokenKycStatus {
  NOT_APPLICABLE,
  GRANTED,
  REVOKED;

  public static TokenKycStatus fromString(String kycStatus) {
    return switch (kycStatus) {
      case "NOT_APPLICABLE" -> TokenKycStatus.NOT_APPLICABLE;
      case "GRANTED" -> TokenKycStatus.GRANTED;
      case "REVOKED" -> TokenKycStatus.REVOKED;
      default -> throw new RuntimeException("Unknown kyc status " + kycStatus);
    };
  }
}
