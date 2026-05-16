package io.github.manishdait.mirrornodeclientj.data;

public enum RegisteredServiceType {
  BLOCK_NODE("BLOCK_NODE"),
  GENERAL_SERVICE("GENERAL_SERVICE"),
  MIRROR_NODE("MIRROR_NODE"),
  RPC_RELAY("RPC_RELAY");

  private final String value;

  RegisteredServiceType(String value) {
    this.value = value;
  }

  public static RegisteredServiceType formString(String value) {
    return switch (value) {
      case "BLOCK_NODE" -> RegisteredServiceType.BLOCK_NODE;
      case "MIRROR_NODE" -> RegisteredServiceType.MIRROR_NODE;
      case "RPC_RELAY" -> RegisteredServiceType.RPC_RELAY;
      case "GENERAL_SERVICE" -> RegisteredServiceType.GENERAL_SERVICE;
      default -> throw new IllegalStateException("Unexpected value: " + value);
    };
  }

  public String getValue() {
    return this.value;
  }
}
