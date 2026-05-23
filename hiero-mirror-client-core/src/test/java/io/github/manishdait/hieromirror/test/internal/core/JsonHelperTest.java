package io.github.manishdait.hieromirror.test.internal.core;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hedera.hashgraph.sdk.Key;
import com.hedera.hashgraph.sdk.PublicKey;
import io.github.manishdait.hieromirror.internal.core.JsonHelper;
import java.util.HexFormat;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

public class JsonHelperTest {
  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  void shouldParseEd25519Key() {
    var json =
        """
     {
      "key": {
        "_type": "ED25519",
         "key": "84e9575e7c9c3f5c553fb4c54e5dc408e41a1d58c12388bea37d7d7365320f6f"
      }
     }
    """;
    var label = "key";
    var jsonNode = mapper.readTree(json);

    var expected =
        PublicKey.fromString("84e9575e7c9c3f5c553fb4c54e5dc408e41a1d58c12388bea37d7d7365320f6f");
    var result = JsonHelper.toKey(jsonNode, label);

    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.toString()).isEqualTo(expected.toString());
  }

  @Test
  void shouldParseEcdsaKey() {
    var json =
        """
     {
      "key": {
        "_type": "ECDSA_SECP256K1",
        "key": "02c9638effeef78801666745d67637c318d44c5a4778bd6bcfd81fadb423b06225"
      }
     }
    """;
    var label = "key";
    var jsonNode = mapper.readTree(json);

    var expected =
        PublicKey.fromStringECDSA(
            "02c9638effeef78801666745d67637c318d44c5a4778bd6bcfd81fadb423b06225");
    var result = JsonHelper.toKey(jsonNode, label);

    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.toString()).isEqualTo(expected.toString());
  }

  @Test
  void shouldParseProtoEncodedKey() throws InvalidProtocolBufferException {
    var json =
        """
     {
      "key": {
        "_type": "ProtobufEncoded",
        "key": "2aa916081112a4160a2a2a28080112240a221220ace0198a26c53cc7d8ca043263de24c5c64f5298654d95b49be7417803ab42c10a722a700801126c0a221220f9af6e1ff46fa9b131d133488bfbf42c520084ba54e5fca945ec4c039d40cb990a221220db67371c4a83fd612a9f6911b602114b37996a721cc117fbce6b8564e944f8690a221220e9031279e8b11c13917d85e096ce86f01313018943b33e23c0d4ff37409c6c530a4e2a4c080112480a221220c50d4e3ab5b21f8a10f622383abcdb8ad5c7338a8e6d5c5384871fa76a9f12620a2212201c44364fecec460af29a08d5a63255e4698e9ceb5d591a4ec88a89b00a220a690a722a700801126c0a221220b8f473c5ebf241b4e97d0ffd5e3024127f02ae198ab820a2f280891d9296b5e50a2212207951837813a4586f8e53239827de929d6f1b6896d71e3061d17805ef984edb920a22122047cd8411e852d835ee00ad75b91eebff9105394acfaaf62d869df0e6316b942a0a22122093e2d05b9d54d76f27b9cb364ba91880c84612a655880829db31583d016a71990a4e2a4c080112480a2212203d535ea9916f2e33320848b1ddc2920e20e6c1eef95ac8221934cb49b99adc870a221220f5a55e171355dad3e81ea73856b1a015d9c62dc216d8e7c6963f915097fc01e80a722a700801126c0a22122035d87f757843a08a9db0d9359a3d90e7afa9dedb79f0d5fa2228c39ab7aa59e50a221220acc9444bae046872c97a086dfef251a6f8afeeba1408748a68e6e511bb63d2fa0a2212205f887c95f8011822167d2a86e7e79e4c8ac0c624f36724671f04cc1ae84dd20d0a4e2a4c080112480a2212203bd9658b40a3128f7097e1c1e13a5471b8a52aea318810ad39c072ee16fc42380a2212203c358b3b036265d1b0fbea1356955831aeac992b0b98e266f343c4712866b7e20a4e2a4c080112480a2212202a9fde4bbc67badc41892a844231342af3a42ab7682b2c850196ae68d3d379540a221220428dc44257a6c51ecd015f933aec4b2aa7019b477d05c667e393e82e2ee719340a4e2a4c080112480a221220917a559aa2e6c051cec0e6c21b368a865410ec4b0a1391ef4339c3e1854c671a0a221220285a09dbab9ab933058c1416074d5d215603f1d0a438a0707cec470ad7dddf740a722a700801126c0a22122079b69cdfd22c0459292e432e0590a3411d0b408ec690109aeabc9b4ebdfc9a560a221220674fc8c6445319536e98b957602fdcaaaf95c742496b9d737b23525b13a1f4220a221220639566ad36fae9678781c028401ae40a1010ad0b1a550d7ce4f902d5381715540a722a700801126c0a221220987057402219b981665387a00206a175a1d35cd556a8a65022799a77925f45ea0a221220025e34ca6d3fcda87f83454975dfadc81db9f1f0f2d4123a8f67a621db86b62f0a221220ff8f459d82dc99da52654ba9b032ad761f3ab2f25eb1f130f0bd1730c3a5a1440a722a700801126c0a221220fed38c9876f4075b2106a82bb4b9d6fd6ee59aa569a07115119d972f47e2ed4c0a221220180ff422ecb8f1f9a89b8f9270510c810fe42f2f3b561bcd41ec6015cf49bd6c0a221220aa5f4534c431dd94c4c82428365767720a53aba3766bc58aca08d4d9ea54259a0a722a700801126c0a221220acef625dba06682dbfdc5aaf1f989344328962aa7806b89b69236a5d021299300a22122001a0dc215a86c07da89f2fa676620a1c2e1d4d42e4cd78c0887879ef8af17e930a2212200a7ed34fbf2a3505e47081a3cb6bc39af9fa68466746090e0abbf13adac26c2d0a722a700801126c0a2212204e53a1bf9eb9a225341d7eeab59fe96da1b006f5cb17f6d73a61e25e666b19fa0a221220ec64431cac231be04600693c7868be0fe450c9e80bf27964f010febf47a51bd80a221220bc04d264220522154be42b0a2bf721a87eb81c244eb9b43f7b9016fc49581e9b0a4e2a4c080112480a22122080c55cfbfaa27a803304864128561272d16c0db51fa6e6ded35fd7f6e5b729fe0a221220c58ce481592080721815e75c62347c82537611a5272a8a0f9ef804351ed2bb510a722a700801126c0a221220c44c911fa45166e356b498463184459dd9ee760bacc083de348691d6357e06340a221220ef2d877b88b7464d9253560b8851316f5c2f6ddf935eb4eec0761a3262b0a48c0a221220a95d54cf49c1d08cd16d8908f37dfad95637134ffaf528a1d96da7f28d45f1390a4e2a4c080112480a221220478d70802c080d60a4703833fc733c92ee021542354c6fcb6a5709aec2f5735e0a221220a8b2ac85e35c856b1500d97dc0f28e876dfcd5b15fbdf7604c4aa924c99394160a4e2a4c080112480a221220ed1e3e61d1257f8683c169e5c884543f836fd5968e1b5f262733948bc7d411330a2212209f1d5f2f38fdfe4bfff64abdedcf3ba251dbb0af288a60cadbf09769faf4a8fa0a722a700801126c0a221220fbe87abd8d40f1f8b7d9a592bcb972a1e2eceddf099fa9d929239ecc4e719f9a0a22122082f39c9e90537ee67254ad489fe2d4fa5841960023489c529f31e8138ddb037e0a221220b908e24ad83fec81f194e4addccb6ffe8c7d12d6048f30be1604449fe949b52f0a4e2a4c080112480a2212202092e2605a6f8e0cd8e290e840e97b6e264f390ace08b72c3305ccd37cedab840a221220b0a84a710e3a9ca2b39011a2abd4de39fa07caedb7b9e01beae5f9135785fa2b0a4e2a4c080112480a22122026f2a5539a4297e00382e3090969e985acb74bb4f7e190875fb5e72e81dfa4c30a221220d779b8d5f620b97b9eda1e5b92cd6d5bc710e533f1231575c8b216da6a727c630a722a700801126c0a2212208a020ccb61144a5d925f59d02a35a1037680928b4a854f6bfe6ce0773ec9bf330a221220108ddd67666d1cdb6b79ae31af71000a3b29725e4e9494899f297709bda8892b0a221220bda0ce405e207c7fe943afff1e0e2c1028da499e6bd2f5b593fea4092757f3860a722a700801126c0a221220724b0d7dd01598e375bb2f996abe72f4cbdf4424ad29652801540bdb1c0315100a221220c4cb11fb0fd3619756c15b4ffffd484be16fc56a81fa71a69856e8a50f66e8450a22122009f2091f332a01e7df629d087c7f69589b9d8fbe93fe26e9837f815cff8e3d9a0a722a700801126c0a2212205f65df41b8f6b3a696c6c67686e7d2633e26780e53e2625e3c05120ad0475a9f0a2212206732c1a03c4ad571683dce9c794308660073bd0331e41e3049e9e4a5b986434a0a221220f7211d10a1cd6ad3997b6e2366e26f2f211a4bd77ff27330037bbdd3dac0ce900a722a700801126c0a221220183c3ef7e2f1a31aa430cd74da61046bda6716338a50edc84cceeebf45e9dcad0a2212202e1fcb4ab6d23adcfadab6a3cb11e0d5fd4824889ec8685d216c3cb7bec400fe0a221220ee67f62419d05cf5029774683d90151896dc27952b533dba418265eb5223c01d0a722a700801126c0a221220d6a38a0b1596816292ecf98055914f9f1c7ef85a92cfbbaabeb1e8df3b4097c80a221220671aa60fc441a429b09282dccfcd17d0f2af31ddf33438e5b70aa3cfa87538640a221220f73c940196512646aa86977ff6464be696818d9750ef65f4a038a98460eaeecf0a4e2a4c080112480a221220725478723849c0f7257fe348c675e37067900409aa95abbf69ddfc5d0b4e457b0a2212208829230ba24a569508f8de25e1892dfd7c5dec038c6000ab7d303c542f61b2f60a722a700801126c0a2212206e80202b193ad40de2f5763fb92cc432ba2ecedcc0a931608d700cabdf237ced0a221220a6ee1781a0c142d5f88343e71c8e67622509a2dec33ec217357e642050bdf4660a221220fb45a66ceefa57448b0fe52028f21a390632f45b407e567decab49119fd426e10a2212201479dccbb46efd5f80b1223b27801d52d3338c28552e4e96312cafd401fc4069"
      }
     }
    """;
    var label = "key";
    var jsonNode = mapper.readTree(json);

    var expected =
        Key.fromBytes(
            HexFormat.of()
                .parseHex(
                    "2aa916081112a4160a2a2a28080112240a221220ace0198a26c53cc7d8ca043263de24c5c64f5298654d95b49be7417803ab42c10a722a700801126c0a221220f9af6e1ff46fa9b131d133488bfbf42c520084ba54e5fca945ec4c039d40cb990a221220db67371c4a83fd612a9f6911b602114b37996a721cc117fbce6b8564e944f8690a221220e9031279e8b11c13917d85e096ce86f01313018943b33e23c0d4ff37409c6c530a4e2a4c080112480a221220c50d4e3ab5b21f8a10f622383abcdb8ad5c7338a8e6d5c5384871fa76a9f12620a2212201c44364fecec460af29a08d5a63255e4698e9ceb5d591a4ec88a89b00a220a690a722a700801126c0a221220b8f473c5ebf241b4e97d0ffd5e3024127f02ae198ab820a2f280891d9296b5e50a2212207951837813a4586f8e53239827de929d6f1b6896d71e3061d17805ef984edb920a22122047cd8411e852d835ee00ad75b91eebff9105394acfaaf62d869df0e6316b942a0a22122093e2d05b9d54d76f27b9cb364ba91880c84612a655880829db31583d016a71990a4e2a4c080112480a2212203d535ea9916f2e33320848b1ddc2920e20e6c1eef95ac8221934cb49b99adc870a221220f5a55e171355dad3e81ea73856b1a015d9c62dc216d8e7c6963f915097fc01e80a722a700801126c0a22122035d87f757843a08a9db0d9359a3d90e7afa9dedb79f0d5fa2228c39ab7aa59e50a221220acc9444bae046872c97a086dfef251a6f8afeeba1408748a68e6e511bb63d2fa0a2212205f887c95f8011822167d2a86e7e79e4c8ac0c624f36724671f04cc1ae84dd20d0a4e2a4c080112480a2212203bd9658b40a3128f7097e1c1e13a5471b8a52aea318810ad39c072ee16fc42380a2212203c358b3b036265d1b0fbea1356955831aeac992b0b98e266f343c4712866b7e20a4e2a4c080112480a2212202a9fde4bbc67badc41892a844231342af3a42ab7682b2c850196ae68d3d379540a221220428dc44257a6c51ecd015f933aec4b2aa7019b477d05c667e393e82e2ee719340a4e2a4c080112480a221220917a559aa2e6c051cec0e6c21b368a865410ec4b0a1391ef4339c3e1854c671a0a221220285a09dbab9ab933058c1416074d5d215603f1d0a438a0707cec470ad7dddf740a722a700801126c0a22122079b69cdfd22c0459292e432e0590a3411d0b408ec690109aeabc9b4ebdfc9a560a221220674fc8c6445319536e98b957602fdcaaaf95c742496b9d737b23525b13a1f4220a221220639566ad36fae9678781c028401ae40a1010ad0b1a550d7ce4f902d5381715540a722a700801126c0a221220987057402219b981665387a00206a175a1d35cd556a8a65022799a77925f45ea0a221220025e34ca6d3fcda87f83454975dfadc81db9f1f0f2d4123a8f67a621db86b62f0a221220ff8f459d82dc99da52654ba9b032ad761f3ab2f25eb1f130f0bd1730c3a5a1440a722a700801126c0a221220fed38c9876f4075b2106a82bb4b9d6fd6ee59aa569a07115119d972f47e2ed4c0a221220180ff422ecb8f1f9a89b8f9270510c810fe42f2f3b561bcd41ec6015cf49bd6c0a221220aa5f4534c431dd94c4c82428365767720a53aba3766bc58aca08d4d9ea54259a0a722a700801126c0a221220acef625dba06682dbfdc5aaf1f989344328962aa7806b89b69236a5d021299300a22122001a0dc215a86c07da89f2fa676620a1c2e1d4d42e4cd78c0887879ef8af17e930a2212200a7ed34fbf2a3505e47081a3cb6bc39af9fa68466746090e0abbf13adac26c2d0a722a700801126c0a2212204e53a1bf9eb9a225341d7eeab59fe96da1b006f5cb17f6d73a61e25e666b19fa0a221220ec64431cac231be04600693c7868be0fe450c9e80bf27964f010febf47a51bd80a221220bc04d264220522154be42b0a2bf721a87eb81c244eb9b43f7b9016fc49581e9b0a4e2a4c080112480a22122080c55cfbfaa27a803304864128561272d16c0db51fa6e6ded35fd7f6e5b729fe0a221220c58ce481592080721815e75c62347c82537611a5272a8a0f9ef804351ed2bb510a722a700801126c0a221220c44c911fa45166e356b498463184459dd9ee760bacc083de348691d6357e06340a221220ef2d877b88b7464d9253560b8851316f5c2f6ddf935eb4eec0761a3262b0a48c0a221220a95d54cf49c1d08cd16d8908f37dfad95637134ffaf528a1d96da7f28d45f1390a4e2a4c080112480a221220478d70802c080d60a4703833fc733c92ee021542354c6fcb6a5709aec2f5735e0a221220a8b2ac85e35c856b1500d97dc0f28e876dfcd5b15fbdf7604c4aa924c99394160a4e2a4c080112480a221220ed1e3e61d1257f8683c169e5c884543f836fd5968e1b5f262733948bc7d411330a2212209f1d5f2f38fdfe4bfff64abdedcf3ba251dbb0af288a60cadbf09769faf4a8fa0a722a700801126c0a221220fbe87abd8d40f1f8b7d9a592bcb972a1e2eceddf099fa9d929239ecc4e719f9a0a22122082f39c9e90537ee67254ad489fe2d4fa5841960023489c529f31e8138ddb037e0a221220b908e24ad83fec81f194e4addccb6ffe8c7d12d6048f30be1604449fe949b52f0a4e2a4c080112480a2212202092e2605a6f8e0cd8e290e840e97b6e264f390ace08b72c3305ccd37cedab840a221220b0a84a710e3a9ca2b39011a2abd4de39fa07caedb7b9e01beae5f9135785fa2b0a4e2a4c080112480a22122026f2a5539a4297e00382e3090969e985acb74bb4f7e190875fb5e72e81dfa4c30a221220d779b8d5f620b97b9eda1e5b92cd6d5bc710e533f1231575c8b216da6a727c630a722a700801126c0a2212208a020ccb61144a5d925f59d02a35a1037680928b4a854f6bfe6ce0773ec9bf330a221220108ddd67666d1cdb6b79ae31af71000a3b29725e4e9494899f297709bda8892b0a221220bda0ce405e207c7fe943afff1e0e2c1028da499e6bd2f5b593fea4092757f3860a722a700801126c0a221220724b0d7dd01598e375bb2f996abe72f4cbdf4424ad29652801540bdb1c0315100a221220c4cb11fb0fd3619756c15b4ffffd484be16fc56a81fa71a69856e8a50f66e8450a22122009f2091f332a01e7df629d087c7f69589b9d8fbe93fe26e9837f815cff8e3d9a0a722a700801126c0a2212205f65df41b8f6b3a696c6c67686e7d2633e26780e53e2625e3c05120ad0475a9f0a2212206732c1a03c4ad571683dce9c794308660073bd0331e41e3049e9e4a5b986434a0a221220f7211d10a1cd6ad3997b6e2366e26f2f211a4bd77ff27330037bbdd3dac0ce900a722a700801126c0a221220183c3ef7e2f1a31aa430cd74da61046bda6716338a50edc84cceeebf45e9dcad0a2212202e1fcb4ab6d23adcfadab6a3cb11e0d5fd4824889ec8685d216c3cb7bec400fe0a221220ee67f62419d05cf5029774683d90151896dc27952b533dba418265eb5223c01d0a722a700801126c0a221220d6a38a0b1596816292ecf98055914f9f1c7ef85a92cfbbaabeb1e8df3b4097c80a221220671aa60fc441a429b09282dccfcd17d0f2af31ddf33438e5b70aa3cfa87538640a221220f73c940196512646aa86977ff6464be696818d9750ef65f4a038a98460eaeecf0a4e2a4c080112480a221220725478723849c0f7257fe348c675e37067900409aa95abbf69ddfc5d0b4e457b0a2212208829230ba24a569508f8de25e1892dfd7c5dec038c6000ab7d303c542f61b2f60a722a700801126c0a2212206e80202b193ad40de2f5763fb92cc432ba2ecedcc0a931608d700cabdf237ced0a221220a6ee1781a0c142d5f88343e71c8e67622509a2dec33ec217357e642050bdf4660a221220fb45a66ceefa57448b0fe52028f21a390632f45b407e567decab49119fd426e10a2212201479dccbb46efd5f80b1223b27801d52d3338c28552e4e96312cafd401fc4069"));
    var result = JsonHelper.toKey(jsonNode, label);

    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.toString()).isEqualTo(expected.toString());
  }

  @Test
  void shouldReturnNullIfFieldNotPresent() {
    var json =
        """
       {
        "key": {
         "_type": "ED25519",
         "key": "84e9575e7c9c3f5c553fb4c54e5dc408e41a1d58c12388bea37d7d7365320f6f"
        }
       }
      """;
    var label = "unknown";
    var jsonNode = mapper.readTree(json);
    var result = JsonHelper.toKey(jsonNode, label);

    Assertions.assertThat(result).isNull();
  }

  @Test
  void shouldReturnNullIfFiledIsNull() {
    var json =
        """
       {
        "key": null
       }
      """;
    var label = "unknown";
    var jsonNode = mapper.readTree(json);
    var result = JsonHelper.toKey(jsonNode, label);

    Assertions.assertThat(result).isNull();
  }

  @Test
  void shouldParseInstantWithNanos() {
    var json =
        """
    {
      "consensus_timestamp": "1678234567.123456789"
    }
    """;
    var label = "consensus_timestamp";
    var jsonNode = mapper.readTree(json);

    var result = JsonHelper.toInstant(jsonNode, label);

    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getEpochSecond()).isEqualTo(1678234567L);
    Assertions.assertThat(result.getNano()).isEqualTo(123456789);
  }

  @Test
  void shouldParseInstantWithShortNanos() {
    var json = "{ \"timestamp\": \"1678234567.123\" }";
    var jsonNode = mapper.readTree(json);

    var result = JsonHelper.toInstant(jsonNode, "timestamp");

    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getNano()).isEqualTo(123000000);
  }

  @Test
  void shouldParseInstantWithoutNanos() {
    var json = "{ \"timestamp\": \"1678234567\" }";
    var jsonNode = mapper.readTree(json);

    var result = JsonHelper.toInstant(jsonNode, "timestamp");

    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getEpochSecond()).isEqualTo(1678234567L);
    Assertions.assertThat(result.getNano()).isZero();
  }

  @Test
  void shouldReturnNullForEmptyString() {
    var json = "{ \"timestamp\": \"\" }";
    var jsonNode = mapper.readTree(json);

    var result = JsonHelper.toInstant(jsonNode, "timestamp");

    Assertions.assertThat(result).isNull();
  }

  @Test
  void shouldReturnNullForMissingField() {
    var json = "{ \"other\": \"123.456\" }";
    var jsonNode = mapper.readTree(json);

    var result = JsonHelper.toInstant(jsonNode, "timestamp");

    Assertions.assertThat(result).isNull();
  }

  @Test
  void shouldReturnNullForJsonNull() {
    var json = "{ \"timestamp\": null }";
    var jsonNode = mapper.readTree(json);

    var result = JsonHelper.toInstant(jsonNode, "timestamp");

    Assertions.assertThat(result).isNull();
  }

  @Test
  void shouldParseNullableString() {
    var json = "{ \"name\": \"Hedera\" }";
    var node = mapper.readTree(json);

    Assertions.assertThat(JsonHelper.toNullableString(node, "name")).isEqualTo("Hedera");
    Assertions.assertThat(JsonHelper.toNullableString(node, "missing")).isNull();
    Assertions.assertThat(JsonHelper.toNullableString(node, "name")).isNotNull();
  }

  @Test
  void shouldConvertBytes() {
    var json = "{ \"data\": \"ABC\" }";
    var node = mapper.readTree(json);

    byte[] expected = "ABC".getBytes();
    Assertions.assertThat(JsonHelper.toBytes(node, "data")).containsExactly(expected);
    Assertions.assertThat(JsonHelper.toBytes(node, "missing")).isNull();
  }

  @Test
  void shouldParseBoolean() {
    var json =
        """
        {
            "is_active": true,
            "is_deleted": false,
            "is_null": null
        }
        """;
    var node = mapper.readTree(json);

    Assertions.assertThat(JsonHelper.toBoolean(node, "is_active")).isTrue();
    Assertions.assertThat(JsonHelper.toBoolean(node, "is_deleted")).isFalse();
    Assertions.assertThat(JsonHelper.toBoolean(node, "is_null")).isFalse();
    Assertions.assertThat(JsonHelper.toBoolean(node, "missing")).isFalse();
  }

  @Test
  void shouldHandleLongs() {
    var json = "{ \"amount\": 5000000000, \"zero\": 0, \"empty\": null }";
    var node = mapper.readTree(json);

    Assertions.assertThat(JsonHelper.toNullableLong(node, "amount")).isEqualTo(5000000000L);
    Assertions.assertThat(JsonHelper.toNullableLong(node, "empty")).isNull();
    Assertions.assertThat(JsonHelper.toNullableLong(node, "missing")).isNull();

    Assertions.assertThat(JsonHelper.toLong(node, "amount")).isEqualTo(5000000000L);
    Assertions.assertThat(JsonHelper.toLong(node, "missing")).isZero();
  }

  @Test
  void shouldParseInt() {
    var json = "{ \"count\": 42 }";
    var node = mapper.readTree(json);

    Assertions.assertThat(JsonHelper.toInt(node, "count")).isEqualTo(42);
    Assertions.assertThat(JsonHelper.toInt(node, "missing")).isZero();
  }
}
