module io.github.manishdait.hieromirror.test {
  requires org.junit.jupiter.api;
  requires org.assertj.core;
  requires org.mockito;
  requires io.github.manishdait.hieromirror;
  requires tools.jackson.databind;

  opens io.github.manishdait.hieromirror.test.internal.core to
      org.junit.platform.commons;
  opens io.github.manishdait.hieromirror.test.query to
      org.junit.platform.commons;
}
