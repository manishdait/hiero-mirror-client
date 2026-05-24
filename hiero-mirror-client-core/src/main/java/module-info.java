module io.github.manishdait.hieromirror {
  requires static org.jspecify;
  requires transitive com.hedera.hashgraph.sdk;
  requires java.net.http;
  requires java.annotation;
  requires tools.jackson.databind;

  exports io.github.manishdait.hieromirror.query;
  exports io.github.manishdait.hieromirror.model;
  exports io.github.manishdait.hieromirror.resource;
  exports io.github.manishdait.hieromirror.resource.wrapper;
  exports io.github.manishdait.hieromirror;
}
