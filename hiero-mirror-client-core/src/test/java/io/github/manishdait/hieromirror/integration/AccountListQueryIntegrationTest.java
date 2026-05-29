package io.github.manishdait.hieromirror.integration;

import io.github.manishdait.hieromirror.HieroNetwork;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.QueryOperator;
import io.github.manishdait.hieromirror.query.AccountListQuery;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountListQueryIntegrationTest {
  private final MirrorNodeClient mirrorNodeClient = new MirrorNodeClient(HieroNetwork.SOLO);

  @Test
  void shouldFetchAccountList() {
    var query = new AccountListQuery();
    var result = query.execute(mirrorNodeClient);

    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.data()).isNotEmpty();
  }

  @Test
  void shouldFetchAccountListWithLimit() {
    var query = new AccountListQuery().setLimit(1);
    var result = query.execute(mirrorNodeClient);

    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.data()).isNotEmpty();
    Assertions.assertThat(result.data()).hasSize(1);
  }

  @Test
  void shouldFetchAccountListWithAccountIdParam() {
    var query = new AccountListQuery().setAccountId(QueryOperator.EQ, "0.0.3");
    var result = query.execute(mirrorNodeClient);

    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.data()).isNotEmpty();
    Assertions.assertThat(result.data()).hasSize(1);
    Assertions.assertThat(result.data().getFirst().accountId().toString()).isEqualTo("0.0.3");
  }
}
