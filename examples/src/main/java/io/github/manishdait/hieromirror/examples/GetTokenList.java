package io.github.manishdait.hieromirror.examples;

import com.hedera.hashgraph.sdk.TokenType;
import io.github.manishdait.hieromirror.HieroNetwork;
import io.github.manishdait.hieromirror.MirrorNodeClient;
import io.github.manishdait.hieromirror.model.Order;
import io.github.manishdait.hieromirror.query.TokenListQuery;
import java.util.List;

public class GetTokenList {
  static void main() {
    var client = new MirrorNodeClient(HieroNetwork.TESTNET);

    var query =
        new TokenListQuery()
            .setOrder(Order.DESC)
            .setLimit(10)
            .addTokenType(TokenType.FUNGIBLE_COMMON);

    var tokens1 = query.execute(client);
    IO.println("Tokens: " + tokens1);

    // Using client resource
    var tokens2 =
        client
            .tokens()
            .findAll()
            .order(Order.DESC)
            .limit(10)
            .type(List.of(TokenType.FUNGIBLE_COMMON))
            .call();
    IO.println("Tokens: " + tokens2);
  }
}
