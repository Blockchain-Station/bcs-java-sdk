package com.enjin.coin.sdk.service.tokens;

import com.enjin.coin.sdk.config.Config;
import com.enjin.coin.sdk.service.tokens.impl.TokensAsyncServiceImpl;
import com.enjin.coin.sdk.util.JsonRpcUtils;
import com.enjin.coin.sdk.vo.token.GetTokenRequestVO;
import com.enjin.coin.sdk.vo.token.GetTokenResponseVO;
import com.enjin.coin.sdk.vo.token.ImmutableGetTokenRequestVO;
import com.enjin.coin.sdk.vo.token.ImmutableGetTokenResponseVO;
import com.enjin.coin.sdk.vo.token.ImmutableListTokensRequestVO;
import com.enjin.coin.sdk.vo.token.ListTokensRequestVO;
import com.enjin.coin.sdk.vo.token.ListTokensResponseVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Map;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TokensAsyncServiceImpl.class)
public class TokensAsyncServiceTest {

    TokensAsyncService tokensAsyncService;
    Config enjinConfig;

    @Before
    public void setUp() {
        enjinConfig = new Config();
    }

    @Test
    public void testContructor() {
        tokensAsyncService = new TokensAsyncServiceImpl(enjinConfig);
        assertThat(tokensAsyncService).isNotNull()
                .satisfies(o -> assertThat(o.toString()).isNotEmpty());
    }

  
    @SuppressWarnings("unchecked")
    @Test
    public void testGetToken_Success() throws Exception {
        GetTokenRequestVO getTokenRequestVO = ImmutableGetTokenRequestVO.builder()
                .setTokenId("tokenId")
                .build();

        GetTokenResponseVO returnedGetTokenResponseVO = ImmutableGetTokenResponseVO.builder().build();

        JsonRpcUtils mockJsonRpcUtils = PowerMockito.mock(JsonRpcUtils.class);
        PowerMockito.whenNew(JsonRpcUtils.class).withNoArguments().thenReturn(mockJsonRpcUtils);
        Mockito.when(mockJsonRpcUtils.sendJsonRpcRequest(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.isA(Map.class))).thenReturn(returnedGetTokenResponseVO);

        tokensAsyncService = new TokensAsyncServiceImpl(enjinConfig);
        Future<GetTokenResponseVO> getTokenResponseFutureVO = tokensAsyncService.getTokenAsync(getTokenRequestVO);
        assertThat(getTokenResponseFutureVO).isNotNull();
        GetTokenResponseVO getTokenResponseVO = getTokenResponseFutureVO.get();
        assertThat(getTokenResponseVO).isNotNull();

        PowerMockito.verifyNew(JsonRpcUtils.class, Mockito.times(1)).withNoArguments();
        Mockito.verify(mockJsonRpcUtils, Mockito.times(1)).sendJsonRpcRequest(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.isA(Map.class));
    }

    
    @SuppressWarnings("unchecked")
    @Test
    public void testListTokens_Success1() throws Exception {
        ListTokensRequestVO listTokensRequestVO = ImmutableListTokensRequestVO.builder()
                .setAfterTokenId("afterTokenId")
                .setAppId("appId")
                .setLimit("limit")
                .build();

        GetTokenResponseVO[] returnedListTokensResponseVO = new GetTokenResponseVO[]{
                ImmutableGetTokenResponseVO.builder().build()
        };

        JsonRpcUtils mockJsonRpcUtils = PowerMockito.mock(JsonRpcUtils.class);
        PowerMockito.whenNew(JsonRpcUtils.class).withNoArguments().thenReturn(mockJsonRpcUtils);
        Mockito.when(mockJsonRpcUtils.sendJsonRpcRequest(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.isA(Map.class))).thenReturn(returnedListTokensResponseVO);

        tokensAsyncService = new TokensAsyncServiceImpl(enjinConfig);
        Future<ListTokensResponseVO> listTokensResponseAsyncVO = tokensAsyncService.listTokensAsync(listTokensRequestVO);
        assertThat(listTokensResponseAsyncVO).isNotNull();
        ListTokensResponseVO listTokensResponseVO = listTokensResponseAsyncVO.get();
        assertThat(listTokensResponseVO).isNotNull()
                .satisfies(o -> assertThat(o.toString()).isNotEmpty())
                .satisfies(o -> assertThat(o.getGetTokensResponseVOArray()).isPresent()
                        .hasValueSatisfying(v -> assertThat(v).hasSize(1)));

        PowerMockito.verifyNew(JsonRpcUtils.class, Mockito.times(1)).withNoArguments();
        Mockito.verify(mockJsonRpcUtils, Mockito.times(1)).sendJsonRpcRequest(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.isA(Map.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testListTokens_Success2() throws Exception {
        ListTokensRequestVO listTokensRequestVO = ImmutableListTokensRequestVO.builder()
                .setAfterTokenId("afterTokenId")
                .setAppId("appId")
                .setLimit("limit")
                .build();

        GetTokenResponseVO[] returnedListTokensResponseVO = new GetTokenResponseVO[]{
                ImmutableGetTokenResponseVO.builder().build(),
                ImmutableGetTokenResponseVO.builder().build()
        };

        JsonRpcUtils mockJsonRpcUtils = PowerMockito.mock(JsonRpcUtils.class);
        PowerMockito.whenNew(JsonRpcUtils.class).withNoArguments().thenReturn(mockJsonRpcUtils);
        Mockito.when(mockJsonRpcUtils.sendJsonRpcRequest(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.isA(Map.class))).thenReturn(returnedListTokensResponseVO);

        tokensAsyncService = new TokensAsyncServiceImpl(enjinConfig);
        Future<ListTokensResponseVO> listTokensResponseAsyncVO = tokensAsyncService.listTokensAsync(listTokensRequestVO);
        assertThat(listTokensResponseAsyncVO).isNotNull();
        ListTokensResponseVO listTokensResponseVO = listTokensResponseAsyncVO.get();
        assertThat(listTokensResponseVO).isNotNull()
                .satisfies(o -> assertThat(o.toString()).isNotEmpty())
                .satisfies(o -> assertThat(o.getGetTokensResponseVOArray()).isPresent()
                        .hasValueSatisfying(v -> assertThat(v).hasSize(2)));

        PowerMockito.verifyNew(JsonRpcUtils.class, Mockito.times(1)).withNoArguments();
        Mockito.verify(mockJsonRpcUtils, Mockito.times(1)).sendJsonRpcRequest(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.isA(Map.class));
    }

}