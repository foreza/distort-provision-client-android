package com.vartyr.distort_client;


// Configure your own endpoints here. I'm not showing you mine!

public class EndpointConfigs {
    private String testUrlEndpoint = "@stringForYourEndpoint here";
    private String stagingUrlEndpoint = "@stringForYourEndpoint here";
    private String productionUrlEndpoint= "@stringForYourEndpoint here";


    public EndpointConfigs(){
        
    }

    public String getTestUrlEndpoint() {
        return testUrlEndpoint;
    }

    public String getStagingUrlEndpoint() {
        return stagingUrlEndpoint;
    }

    public String getProductionUrlEndpoint() {
        return productionUrlEndpoint;
    }
}
