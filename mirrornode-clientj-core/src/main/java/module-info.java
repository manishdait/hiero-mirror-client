module io.github.manishdait.mirrornode.clientj.core {
  requires static org.jspecify;
  requires transitive com.hedera.hashgraph.sdk;
  requires java.net.http;
  requires java.annotation;
  requires tools.jackson.databind;

  exports io.github.manishdait.mirrornodeclientj.core;
  exports io.github.manishdait.mirrornodeclientj.core.data;
  exports io.github.manishdait.mirrornodeclientj.core.query;
}
