package com.example.demo.external;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("외부 API 연동 관련 기능 테스트")
public class ExternalAPITest {

    @DisplayName("외부 API 연동")
    @Test
    void test() {
        String ok = "ok";
        ExternalAPI.ExternalAPIInterface apiInterface = Mockito.mock(ExternalAPI.DefaultExternalAPI.class);
        Mockito.when(apiInterface.externalApi()).thenReturn(ok);

        ExternalAPI externalAPI = new ExternalAPI(apiInterface);
        String response = externalAPI.callExternalApi();
        assertThat(response).isEqualTo(ok);
    }
}
