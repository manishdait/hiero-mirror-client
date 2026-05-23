module io.github.manishdait.mirrornodeclientj.core.test {
  requires org.junit.jupiter.api;
  requires org.assertj.core;
  requires org.mockito;
  requires io.github.manishdait.mirrornodeclientj.core;
  requires tools.jackson.databind;

  opens io.github.manishdait.mirrornodeclientj.core.test.internal.core to
    org.junit.platform.commons;

  opens io.github.manishdait.mirrornodeclientj.core.test.internal.parser to
    org.junit.platform.commons;

  opens io.github.manishdait.mirrornodeclientj.core.test.query to
      org.junit.platform.commons;
}
