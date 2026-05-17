package io.github.manishdait.mirrornodeclientj.core.data;

public enum TokenFreezeStatus {
  NOT_APPLICABLE,
  FROZEN,
  UNFROZEN;

  public static TokenFreezeStatus fromString(String freezeStatus) {
    return switch (freezeStatus) {
      case "NOT_APPLICABLE" -> TokenFreezeStatus.NOT_APPLICABLE;
      case "FROZEN" -> TokenFreezeStatus.FROZEN;
      case "UNFROZEN" -> TokenFreezeStatus.UNFROZEN;
      default -> throw new RuntimeException("Unknown freeze status " + freezeStatus);
    };
  }
}
