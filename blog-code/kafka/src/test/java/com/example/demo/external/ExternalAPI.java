package com.example.demo.external;

import java.util.Random;

public class ExternalAPI {

    private final ExternalAPIInterface externalApi;

    public ExternalAPI(ExternalAPIInterface externalApi) {
        this.externalApi = externalApi;
    }

    interface ExternalAPIInterface {
        String externalApi();
    }

    static class DefaultExternalAPI implements ExternalAPIInterface {
        @Override
        public String externalApi() {
            Random random = new Random();
            int result = random.nextInt(10);
            if (result > 5) {
                return "error";
            }
            return "ok";
        }
    }

    public String callExternalApi() {
        return externalApi.externalApi();
    }
}
