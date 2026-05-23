module io.github.manishdait.hieromirror {
  requires static org.jspecify;
  requires transitive com.hedera.hashgraph.sdk;
  requires java.net.http;
  requires java.annotation;
  requires tools.jackson.databind;

  exports io.github.manishdait.mirrornodeclientj.core;
  exports io.github.manishdait.hieromirror.model;
  exports io.github.manishdait.hieromirror.query;
  exports io.github.manishdait.hieromirror.internal.core to
      io.github.manishdait.mirrornodeclientj.core.test;
  exports io.github.manishdait.hieromirror.internal.parser to
      io.github.manishdait.mirrornodeclientj.core.test;
  exports io.github.manishdait.hieromirror.internal.resource to
      io.github.manishdait.mirrornodeclientj.core.test;
  exports io.github.manishdait.hieromirror;
}
