package setapi.plugin.lib.config;


import setapi.plugin.lib.service.RequestBuildingService;

public final class RequestBuilderConfig {
    private static RequestBuildingService instance;

    private RequestBuilderConfig() {
    }

    public static RequestBuildingService getRequestBuilder() {
        if (instance == null) {
            instance = new RequestBuildingService();
        }
        return instance;
    }
}
