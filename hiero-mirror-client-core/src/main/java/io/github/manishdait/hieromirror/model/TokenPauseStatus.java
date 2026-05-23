package io.github.manishdait.hieromirror.model;

public enum TokenPauseStatus {
  NOT_APPLICABLE,
  PAUSED,
  UNPAUSED;

  public static TokenPauseStatus fromString(String value) {
    return switch (value) {
      case "NOT_APPLICABLE" -> TokenPauseStatus.NOT_APPLICABLE;
      case "PAUSED" -> TokenPauseStatus.PAUSED;
      case "UNPAUSED" -> TokenPauseStatus.UNPAUSED;
      default -> throw new RuntimeException("Invalid pause status " + value);
    };
  }
}
