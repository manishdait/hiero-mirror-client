package io.github.manishdait.mirrornodeclientj.core.test.query;

import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.query.AccountListQuery;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AccountListQueryTest {
  private final MirrorNodeClient client = Mockito.mock(MirrorNodeClient.class);

  @Test
  void shouldCreateAccountListQueryWithDefaults() {
    var query = new AccountListQuery(client);
    Assertions.assertThat(query).isNotNull();
    Assertions.assertThat(query.getOrder()).isEqualTo(Order.ASC);
    Assertions.assertThat(query.getLimit()).isEqualTo(25);
    Assertions.assertThat(query.isBalance()).isFalse();
    Assertions.assertThat(query.getPublicKey()).isNull();
    Assertions.assertThat(query.getAccountId()).isNull();
    Assertions.assertThat(query.getAccountBalance()).isNull();
  }
}
