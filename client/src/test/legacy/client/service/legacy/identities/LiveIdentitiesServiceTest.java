package client.service.legacy.identities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import io.enjincoin.sdk.client.service.BaseLiveServiceTest;
import io.enjincoin.sdk.client.service.identities.AsynchronousIdentitiesService;
import io.enjincoin.sdk.client.service.identities.SynchronousIdentitiesService;
import io.enjincoin.sdk.client.service.identities.vo.CreateIdentityRequestBody;
import io.enjincoin.sdk.client.service.identities.vo.CreateIdentityResponseBody;
import io.enjincoin.sdk.client.service.identities.vo.GetIdentityResponseBody;
import io.enjincoin.sdk.client.service.identities.vo.IdentityField;
import io.enjincoin.sdk.client.service.identities.vo.LinkIdentityRequestBody;
import io.enjincoin.sdk.client.service.identities.vo.LinkIdentityResponseBody;
import io.enjincoin.sdk.client.service.identities.vo.UpdateIdentityRequestBody;
import io.enjincoin.sdk.client.service.identities.vo.UpdateIdentityResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Calls out to the actual api
 * Will have the disabled annotation in place usually but will be useful for testing the actual api
 *
 */
public class LiveIdentitiesServiceTest extends BaseLiveServiceTest{


    @Test
    public void testSynchronousIdentitiesService_GetIdentities() throws IOException {
        SynchronousIdentitiesService identitiesService = this.client.getIdentitiesService();
        assertThat(identitiesService).isNotNull();

        Response<GetIdentityResponseBody[]> getIdentityResponseVOArray = identitiesService.getIdentitiesSync();
        assertThat(getIdentityResponseVOArray).isNotNull();
        assertThat(getIdentityResponseVOArray.body()).isNotNull();

        for (GetIdentityResponseBody getIdentityResponseVO : getIdentityResponseVOArray.body()) {
            if (getIdentityResponseVO.getFields().isPresent()) {

                for (IdentityField fieldVO : getIdentityResponseVO.getFields().get()) {
                    assertThat(fieldVO).isNotNull();
                }
            }
        }
    }

    @Test
    public void testAsychronousIdentitiesService_GetIdentities() throws InterruptedException, ExecutionException {
        AsynchronousIdentitiesService identitiesService = this.client.getIdentitiesService();
        assertThat(identitiesService).isNotNull();

        //CompletableFuture<GetIdentityResponseBody[]> getIdentityResponseVOArrayCf =
        identitiesService.getIdentitiesAsync(new Callback<GetIdentityResponseBody[]>() {

            @Override
            public void onResponse(Call<GetIdentityResponseBody[]> call, Response<GetIdentityResponseBody[]> getIdentityResponseVOArrayR) {
                assertThat(getIdentityResponseVOArrayR).isNotNull();
                assertThat(getIdentityResponseVOArrayR.body()).isNotNull();
                GetIdentityResponseBody[] getIdentityResponseVOArray = getIdentityResponseVOArrayR.body();

                for (GetIdentityResponseBody getIdentityResponseVO : getIdentityResponseVOArray) {
                    if (getIdentityResponseVO.getFields().isPresent()) {

                        for (IdentityField fieldVO : getIdentityResponseVO.getFields().get()) {
                            assertThat(fieldVO).isNotNull();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<GetIdentityResponseBody[]> call, Throwable t) {
                    fail("Test Failed");
            }
        });

    }

    @Test
    public void testSynchronousIdentitiesService_GetIdentitiesWithFilter() throws IOException {
        SynchronousIdentitiesService identitiesService = this.client.getIdentitiesService();
        assertThat(identitiesService).isNotNull();

        List<String> keyList =  new LinkedList<String>();
        keyList.add("player_name");
        List<String> fieldValueList =  new LinkedList<String>();
        fieldValueList.add("Player One");

        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("key", keyList);
        filterMap.put("field_value", fieldValueList);

        Response<GetIdentityResponseBody[]> getIdentityResponseVOArray = identitiesService.getIdentitiesSync(filterMap);
        assertThat(getIdentityResponseVOArray).isNotNull();
        assertThat(getIdentityResponseVOArray.body()).isNotNull();

        for (GetIdentityResponseBody getIdentityResponseVO : getIdentityResponseVOArray.body()) {
            if (getIdentityResponseVO.getFields().isPresent()) {

                for (IdentityField fieldVO : getIdentityResponseVO.getFields().get()) {
                    assertThat(fieldVO).isNotNull();
                }
            }
        }
    }

    @Test
    public void testAsychronousIdentitiesService_GetIdentitiesWithFilter() throws InterruptedException, ExecutionException {
        AsynchronousIdentitiesService identitiesService = this.client.getIdentitiesService();
        assertThat(identitiesService).isNotNull();

        List<String> keyList =  new LinkedList<String>();
        keyList.add("player_name");
        List<String> fieldValueList =  new LinkedList<String>();
        fieldValueList.add("Player One");

        Map<String, Object> filterMap = new HashMap<>();
        filterMap.put("key", keyList);
        filterMap.put("field_value", fieldValueList);

        //CompletableFuture<GetIdentityResponseBody[]> getIdentityResponseVOArrayCf = identitiesService.getIdentitiesAsync(filterMap);
        identitiesService.getIdentitiesAsync(filterMap, new Callback<GetIdentityResponseBody[]>() {

            @Override
            public void onResponse(Call<GetIdentityResponseBody[]> call, Response<GetIdentityResponseBody[]> response) {
                assertThat(response).isNotNull();
                assertThat(response.body()).isNotNull();
                GetIdentityResponseBody[] getIdentityResponseVOArray = response.body();

                for (GetIdentityResponseBody getIdentityResponseVO : getIdentityResponseVOArray) {
                    if (getIdentityResponseVO.getFields().isPresent()) {

                        for (IdentityField fieldVO : getIdentityResponseVO.getFields().get()) {
                            assertThat(fieldVO).isNotNull();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetIdentityResponseBody[]> call, Throwable t) {
                fail("Test Failed");
            }
        });

    }

    @Test
    public void testSynchronousIdentitiesService_GetIdentityById() throws IOException {
        SynchronousIdentitiesService identitiesService = this.client.getIdentitiesService();
        assertThat(identitiesService).isNotNull();

        String ethereumAddress = "TestEthereumAddress_"+System.currentTimeMillis();
        CreateIdentityRequestBody createIdentityRequestBody = new CreateIdentityRequestBody(ethereumAddress, null);
        Response<CreateIdentityResponseBody> createIdentityResponseVO = identitiesService.createIdentitySync(createIdentityRequestBody);
        assertThat(createIdentityResponseVO).isNotNull();
        assertThat(createIdentityResponseVO.body()).isNotNull();
        assertThat(createIdentityResponseVO.body().getId()).isNotNull();
        assertThat(createIdentityResponseVO.body().getCreatedAt()).isNotNull();
        assertThat(createIdentityResponseVO.body().getUpdatedAt()).isNotNull();

        Integer identityId = createIdentityResponseVO.body().getId().get();

        Response<GetIdentityResponseBody> getIdentityByIdResponseVO = identitiesService.getIdentitySync(identityId);
        assertThat(getIdentityByIdResponseVO).isNotNull();
        assertThat(getIdentityByIdResponseVO.body()).isNotNull();

        if (getIdentityByIdResponseVO.body().getFields().isPresent()) {

            for (IdentityField fieldVO : getIdentityByIdResponseVO.body().getFields().get()) {
                assertThat(fieldVO).isNotNull();
            }
        }

        Response<Boolean> deleteIdentityResult = identitiesService.deleteIdentitySync(identityId);
        assertThat(deleteIdentityResult).isNotNull();
        assertThat(deleteIdentityResult.body()).isTrue();
    }

    @Test
    public void testAsychronousIdentitiesService_GetIdentityById() throws InterruptedException, ExecutionException {
        AsynchronousIdentitiesService identitiesService = this.client.getIdentitiesService();
        assertThat(identitiesService).isNotNull();

        String ethereumAddress = "TestEthereumAddress_"+System.currentTimeMillis();
        CreateIdentityRequestBody createIdentityRequestVO = new CreateIdentityRequestBody(ethereumAddress, null);
        identitiesService.createIdentityAsync(createIdentityRequestVO, new Callback<CreateIdentityResponseBody>() {

            @Override
            public void onResponse(Call<CreateIdentityResponseBody> call, Response<CreateIdentityResponseBody> response) {
                assertThat(response).isNotNull();
                assertThat(response.body()).isNotNull();

                CreateIdentityResponseBody createIdentityResponseVO = response.body();
                assertThat(createIdentityResponseVO.getId()).isNotNull();
                assertThat(createIdentityResponseVO.getCreatedAt()).isNotNull();
                assertThat(createIdentityResponseVO.getUpdatedAt()).isNotNull();

                Integer identityId = createIdentityResponseVO.getId().get();

                identitiesService.getIdentityAsync(identityId, new Callback<GetIdentityResponseBody>() {

                    @Override
                    public void onResponse(Call<GetIdentityResponseBody> call, Response<GetIdentityResponseBody> response) {
                        assertThat(response).isNotNull();
                        assertThat(response.body()).isNotNull();

                        GetIdentityResponseBody getIdentityByIdResponseVO = response.body();
                        if (getIdentityByIdResponseVO.getFields().isPresent()) {

                            for (IdentityField fieldVO : getIdentityByIdResponseVO.getFields().get()) {
                                assertThat(fieldVO).isNotNull();
                            }
                        }

                        identitiesService.deleteIdentityAsync(identityId, new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                assertThat(response).isNotNull();
                                assertThat(response.body()).isNotNull();
                                assertThat(response.body()).isTrue();
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                fail("Test Failed");
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<GetIdentityResponseBody> call, Throwable t) {
                        fail("Test Failed");
                    }

                });

            }

            @Override
            public void onFailure(Call<CreateIdentityResponseBody> call, Throwable t) {
                fail("Test failed");
            }
        });

    }

    @Test
    public void testSynchronousIdentitiesService_CreateIdentity() throws IOException {
        SynchronousIdentitiesService identitiesService = this.client.getIdentitiesService();
        assertThat(identitiesService).isNotNull();

        String ethereumAddress = "TestEthereumAddress_"+System.currentTimeMillis();
        CreateIdentityRequestBody createIdentityRequestBody = new CreateIdentityRequestBody(ethereumAddress, null);
        Response<CreateIdentityResponseBody> createIdentityResponseVO = identitiesService.createIdentitySync(createIdentityRequestBody);
        assertThat(createIdentityResponseVO).isNotNull();
        assertThat(createIdentityResponseVO.body()).isNotNull();
        assertThat(createIdentityResponseVO.body().getId()).isNotNull();
        assertThat(createIdentityResponseVO.body().getCreatedAt()).isNotNull();
        assertThat(createIdentityResponseVO.body().getUpdatedAt()).isNotNull();

        Integer identityId = createIdentityResponseVO.body().getId().get();

        Response<GetIdentityResponseBody> getIdentityByIdResponseVO = identitiesService.getIdentitySync(identityId);
        assertThat(getIdentityByIdResponseVO).isNotNull();
        assertThat(getIdentityByIdResponseVO.body()).isNotNull();

        if (getIdentityByIdResponseVO.body().getFields().isPresent()) {

            for (IdentityField fieldVO : getIdentityByIdResponseVO.body().getFields().get()) {
                assertThat(fieldVO).isNotNull();
            }
        }

        Response<Boolean> deleteIdentityResult = identitiesService.deleteIdentitySync(identityId);
        assertThat(deleteIdentityResult).isNotNull();
        assertThat(deleteIdentityResult.body()).isTrue();
    }

    @Test
    public void testAsychronousIdentitiesService_CreateIdentity() throws InterruptedException, ExecutionException {
        AsynchronousIdentitiesService identitiesService = this.client.getIdentitiesService();
        assertThat(identitiesService).isNotNull();

        String ethereumAddress = "TestEthereumAddress_"+System.currentTimeMillis();
        CreateIdentityRequestBody createIdentityRequestVO = new CreateIdentityRequestBody(ethereumAddress, null);
        identitiesService.createIdentityAsync(createIdentityRequestVO, new Callback<CreateIdentityResponseBody>() {

            @Override
            public void onResponse(Call<CreateIdentityResponseBody> call, Response<CreateIdentityResponseBody> response) {
                assertThat(response).isNotNull();
                assertThat(response.body()).isNotNull();

                CreateIdentityResponseBody createIdentityResponseVO = response.body();
                assertThat(createIdentityResponseVO.getId()).isNotNull();
                assertThat(createIdentityResponseVO.getCreatedAt()).isNotNull();
                assertThat(createIdentityResponseVO.getUpdatedAt()).isNotNull();

                Integer identityId = createIdentityResponseVO.getId().get();

                identitiesService.getIdentityAsync(identityId, new Callback<GetIdentityResponseBody>() {

                    @Override
                    public void onResponse(Call<GetIdentityResponseBody> call, Response<GetIdentityResponseBody> response) {
                        assertThat(response).isNotNull();
                        assertThat(response.body()).isNotNull();

                        GetIdentityResponseBody getIdentityByIdResponseVO = response.body();
                        if (getIdentityByIdResponseVO.getFields().isPresent()) {

                            for (IdentityField fieldVO : getIdentityByIdResponseVO.getFields().get()) {
                                assertThat(fieldVO).isNotNull();
                            }
                        }

                        identitiesService.deleteIdentityAsync(identityId, new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                assertThat(response).isNotNull();
                                assertThat(response.body()).isNotNull();
                                assertThat(response.body()).isTrue();
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                fail("Test Failed");
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<GetIdentityResponseBody> call, Throwable t) {
                        fail("Test Failed");
                    }
                });

            }

            @Override
            public void onFailure(Call<CreateIdentityResponseBody> call, Throwable t) {
                fail("Test Failed");
            }
        });

    }

    @Test
    public void testSynchronousIdentitiesService_DeleteIdentity() throws IOException {
        SynchronousIdentitiesService identitiesService = this.client.getIdentitiesService();
        assertThat(identitiesService).isNotNull();

        String ethereumAddress = "TestEthereumAddress_"+System.currentTimeMillis();
        CreateIdentityRequestBody createIdentityRequestBody = new CreateIdentityRequestBody(ethereumAddress, null);
        Response<CreateIdentityResponseBody> createIdentityResponseVO = identitiesService.createIdentitySync(createIdentityRequestBody);
        assertThat(createIdentityResponseVO).isNotNull();
        assertThat(createIdentityResponseVO.body()).isNotNull();
        assertThat(createIdentityResponseVO.body().getId()).isNotNull();
        assertThat(createIdentityResponseVO.body().getCreatedAt()).isNotNull();
        assertThat(createIdentityResponseVO.body().getUpdatedAt()).isNotNull();

        Integer identityId = createIdentityResponseVO.body().getId().get();

        Response<Boolean> deleteIdentityResult = identitiesService.deleteIdentitySync(identityId);
        assertThat(deleteIdentityResult).isNotNull();
        assertThat(deleteIdentityResult.body()).isTrue();
    }

    @Test
    public void testAsychronousIdentitiesService_DeleteIdentity() throws InterruptedException, ExecutionException {
        AsynchronousIdentitiesService identitiesService = this.client.getIdentitiesService();
        assertThat(identitiesService).isNotNull();

        String ethereumAddress = "TestEthereumAddress_"+System.currentTimeMillis();
        CreateIdentityRequestBody createIdentityRequestVO = new CreateIdentityRequestBody(ethereumAddress, null);
        identitiesService.createIdentityAsync(createIdentityRequestVO, new Callback<CreateIdentityResponseBody>( ) {

            @Override
            public void onResponse(Call<CreateIdentityResponseBody> call, Response<CreateIdentityResponseBody> response) {
                assertThat(response).isNotNull();
                assertThat(response.body()).isNotNull();

                CreateIdentityResponseBody createIdentityResponseVO = response.body();
                assertThat(createIdentityResponseVO.getId()).isNotNull();
                assertThat(createIdentityResponseVO.getCreatedAt()).isNotNull();
                assertThat(createIdentityResponseVO.getUpdatedAt()).isNotNull();

                Integer identityId = createIdentityResponseVO.getId().get();

                identitiesService.deleteIdentityAsync(identityId, new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        assertThat(response).isNotNull();
                        assertThat(response.body()).isNotNull();
                        assertThat(response.body()).isTrue();
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        fail("Test Failed");
                    }
                });
            }

            @Override
            public void onFailure(Call<CreateIdentityResponseBody> call, Throwable t) {
                fail("Test Failed");
            }

        });
    }

    @Test
    public void testSynchronousIdentitiesService_UpdateIdentity() throws IOException {
        SynchronousIdentitiesService identitiesService = this.client.getIdentitiesService();
        assertThat(identitiesService).isNotNull();

        String ethereumAddress = "TestEthereumAddress_"+System.currentTimeMillis();
        CreateIdentityRequestBody createIdentityRequestBody = new CreateIdentityRequestBody(ethereumAddress, null);
        Response<CreateIdentityResponseBody> createIdentityResponseVO = identitiesService.createIdentitySync(createIdentityRequestBody);
        assertThat(createIdentityResponseVO).isNotNull();
        assertThat(createIdentityResponseVO.body()).isNotNull();
        assertThat(createIdentityResponseVO.body().getId()).isNotNull();
        assertThat(createIdentityResponseVO.body().getCreatedAt()).isNotNull();
        assertThat(createIdentityResponseVO.body().getUpdatedAt()).isNotNull();

        Integer identityId = createIdentityResponseVO.body().getId().get();

        IdentityField field1 = new IdentityField(Optional.of("player_name"), Optional.of("Player Ten"), Optional.of(1), Optional.of(1), Optional.of(1));
        IdentityField field2 = new IdentityField(Optional.of("New Field"), Optional.of("New Field Data"), Optional.of(1), Optional.of(1), Optional.of(1));
        IdentityField[] fieldsArray = new IdentityField[] {field1, field2};
        Optional<IdentityField[]> fields = Optional.of(fieldsArray);

        UpdateIdentityRequestBody updateIdentityRequestBody = new UpdateIdentityRequestBody(fields);
        Response<UpdateIdentityResponseBody> updateIdentityResponseVO = identitiesService.updateIdentitySync(identityId, updateIdentityRequestBody);
        assertThat(updateIdentityResponseVO).isNotNull();
        assertThat(updateIdentityResponseVO.body()).isNotNull();
        assertThat(updateIdentityResponseVO.body().getId()).isNotNull();
        assertThat(updateIdentityResponseVO.body().getEthereumAddress()).isNotNull();
        assertThat(updateIdentityResponseVO.body().getCreatedAt()).isNotNull();
        assertThat(updateIdentityResponseVO.body().getUpdatedAt()).isNotNull();
        //assertThat(updateIdentityResponseVO.getUserId()).isNotNull();
        assertThat(updateIdentityResponseVO.body().getLinkingCode()).isNotNull();

        Response<Boolean> deleteIdentityResult = identitiesService.deleteIdentitySync(identityId);
        assertThat(deleteIdentityResult).isNotNull();
        assertThat(deleteIdentityResult.body()).isTrue();
    }

    @Test
    public void testAsychronousIdentitiesService_UpdateIdentity() throws InterruptedException, ExecutionException {
        AsynchronousIdentitiesService identitiesService = this.client.getIdentitiesService();
        assertThat(identitiesService).isNotNull();

        String ethereumAddress = "TestEthereumAddress_"+System.currentTimeMillis();
        CreateIdentityRequestBody createIdentityRequestVO = new CreateIdentityRequestBody(ethereumAddress, null);
        identitiesService.createIdentityAsync(createIdentityRequestVO, new Callback<CreateIdentityResponseBody>() {

            @Override
            public void onResponse(Call<CreateIdentityResponseBody> call, Response<CreateIdentityResponseBody> response) {
                assertThat(response).isNotNull();
                assertThat(response.body()).isNotNull();

                CreateIdentityResponseBody createIdentityResponseVO = response.body();
                assertThat(createIdentityResponseVO.getId()).isNotNull();
                assertThat(createIdentityResponseVO.getCreatedAt()).isNotNull();
                assertThat(createIdentityResponseVO.getUpdatedAt()).isNotNull();

                Integer identityId = createIdentityResponseVO.getId().get();

                IdentityField field1 = new IdentityField(Optional.of("player_name"), Optional.of("Player Ten"), Optional.of(1), Optional.of(1), Optional.of(1));
                IdentityField field2 = new IdentityField(Optional.of("New Field"), Optional.of("New Field Data"), Optional.of(1), Optional.of(1), Optional.of(1));
                IdentityField[] fieldsArray = new IdentityField[] {field1, field2};
                Optional<IdentityField[]> fields = Optional.of(fieldsArray);

                UpdateIdentityRequestBody updateIdentityRequestVO = new UpdateIdentityRequestBody(fields);
                identitiesService.updateIdentityAsync(identityId, updateIdentityRequestVO, new Callback<UpdateIdentityResponseBody>() {

                    @Override
                    public void onResponse(Call<UpdateIdentityResponseBody> call, Response<UpdateIdentityResponseBody> response) {
                        assertThat(response).isNotNull();
                        assertThat(response.body()).isNotNull();

                        UpdateIdentityResponseBody updateIdentityResponseVO = response.body();
                        assertThat(updateIdentityResponseVO.getId()).isNotNull();
                        assertThat(updateIdentityResponseVO.getEthereumAddress()).isNotNull();
                        assertThat(updateIdentityResponseVO.getCreatedAt()).isNotNull();
                        assertThat(updateIdentityResponseVO.getUpdatedAt()).isNotNull();
                        //assertThat(updateIdentityResponseVO.getUserId()).isNotNull();
                        assertThat(updateIdentityResponseVO.getLinkingCode()).isNotNull();

                        identitiesService.deleteIdentityAsync(identityId, new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                assertThat(response).isNotNull();
                                assertThat(response.body()).isNotNull();
                                assertThat(response.body()).isTrue();
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                fail("Test Failed");
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<UpdateIdentityResponseBody> call, Throwable t) {
                        fail("Test Failed");
                    }
                });

            }

            @Override
            public void onFailure(Call<CreateIdentityResponseBody> call, Throwable t) {
                fail("Test Failed");
            }

        });


    }


    @Test
    public void testSynchronousIdentitiesService_LinkIdentity() throws IOException {
        SynchronousIdentitiesService identitiesService = this.client.getIdentitiesService();
        assertThat(identitiesService).isNotNull();

        String ethereumAddress = "TestEthereumAddress_"+System.currentTimeMillis();

        String linkingCode = "av"+System.currentTimeMillis();
        LinkIdentityRequestBody linkIdentityRequestVO = new LinkIdentityRequestBody(ethereumAddress);
        Response<LinkIdentityResponseBody> linkIdentityResponseVO = identitiesService.linkIdentitySync(linkingCode, linkIdentityRequestVO);
        assertThat(linkIdentityResponseVO).isNotNull();
        assertThat(linkIdentityResponseVO.body()).isNotNull();
        //assertThat(linkIdentityResponseVO.body().getId()).isNotNull();
        //assertThat(linkIdentityResponseVO.body().getEthereumAddress()).isNotNull();
        //assertThat(linkIdentityResponseVO.body().getCreatedAt()).isNotNull();
        //assertThat(linkIdentityResponseVO.body().getUpdatedAt()).isNotNull();
        //assertThat(linkIdentityResponseVO.body().getUserId()).isNotNull();
        //assertThat(linkIdentityResponseVO.body().getLinkingCode()).isNotNull();
    }

    @Test
    public void testAsychronousIdentitiesService_LinkIdentity() throws InterruptedException, ExecutionException {
        AsynchronousIdentitiesService identitiesService = this.client.getIdentitiesService();
        assertThat(identitiesService).isNotNull();

        String ethereumAddress = "TestEthereumAddress_"+System.currentTimeMillis();


        String linkingCode = "av"+System.currentTimeMillis();
        LinkIdentityRequestBody linkIdentityRequestVO = new LinkIdentityRequestBody(ethereumAddress);
        identitiesService.linkIdentityAsync(linkingCode, linkIdentityRequestVO, new Callback<LinkIdentityResponseBody>() {

            @Override
            public void onResponse(Call<LinkIdentityResponseBody> call, Response<LinkIdentityResponseBody> response) {
                assertThat(response).isNotNull();
                assertThat(response.body()).isNotNull();

                LinkIdentityResponseBody linkIdentityResponseVO = response.body();
                assertThat(linkIdentityResponseVO).isNotNull();
                //assertThat(linkIdentityResponseVO.getId()).isNotNull();
                //assertThat(linkIdentityResponseVO.getEthereumAddress()).isNotNull();
                //assertThat(linkIdentityResponseVO.getCreatedAt()).isNotNull();
                //assertThat(linkIdentityResponseVO.getUpdatedAt()).isNotNull();
                //assertThat(linkIdentityResponseVO.getUserId()).isNotNull();
                //assertThat(linkIdentityResponseVO.getLinkingCode()).isNotNull();
            }

            @Override
            public void onFailure(Call<LinkIdentityResponseBody> call, Throwable t) {
                fail("Test failed");
            }
        });


    }
}
