package io.github.manishdait.hieromirror.integration;

import com.hedera.hashgraph.sdk.AccountCreateTransaction;
import com.hedera.hashgraph.sdk.PrivateKey;
import io.github.manishdait.hieromirror.HieroNetwork;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.query.AccountQuery;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountQueryIntegrationTest {
  private final MirrorNodeClient mirrorNodeClient = new MirrorNodeClient(HieroNetwork.SOLO);

  @Test
  void shouldFetchAccountById() throws Exception {
    var key = PrivateKey.generateECDSA();
    var memo = "testAccount";

    var env = new IntegrationTestEnv();
    var receipt =
        new AccountCreateTransaction()
            .setKeyWithoutAlias(key)
            .setAccountMemo(memo)
            .execute(env.client)
            .getReceipt(env.client);

    var accountId = receipt.accountId;

    Thread.sleep(10000);

    var result = new AccountQuery().setAccountId(accountId).execute(mirrorNodeClient);
    env.close();

    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result).isPresent();

    var accountInfo = result.get();

    Assertions.assertThat(accountInfo.accountId()).isEqualTo(accountId);
    Assertions.assertThat(accountInfo.key().toBytes()).isEqualTo(key.getPublicKey().toBytes());
    Assertions.assertThat(accountInfo.memo()).isEqualTo(memo);
  }
}
