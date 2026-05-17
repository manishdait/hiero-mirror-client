module io.github.manishdait.mirrornodeclientj.core {
  requires static org.jspecify;
  requires transitive com.hedera.hashgraph.sdk;
  requires java.net.http;
  requires java.annotation;
  requires tools.jackson.databind;

  exports io.github.manishdait.mirrornodeclientj.core;
  exports io.github.manishdait.mirrornodeclientj.core.data;
  exports io.github.manishdait.mirrornodeclientj.core.query;
  exports io.github.manishdait.mirrornodeclientj.core.internal.core to
      io.github.manishdait.mirrornodeclientj.core.test;
  exports io.github.manishdait.mirrornodeclientj.core.internal.parser to
      io.github.manishdait.mirrornodeclientj.core.test;
  exports io.github.manishdait.mirrornodeclientj.core.internal.resource to
      io.github.manishdait.mirrornodeclientj.core.test;
}
