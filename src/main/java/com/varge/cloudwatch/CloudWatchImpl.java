package com.varge.cloudwatch;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.varge.modules.CloudwatchModule;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.ListMetricsRequest;
import software.amazon.awssdk.services.cloudwatch.model.ListMetricsResponse;

public class CloudWatchImpl {

    private final CloudWatchClient cloudWatchClient;
    private String arnRole;

    @Inject
    public CloudWatchImpl(CloudWatchClient cloudWatchClient,
                          @Named("arnRole") String arnRole) {
        this.arnRole = arnRole;
        this.cloudWatchClient = cloudWatchClient;
    }

    public void listMetrics() {
        System.out.println("arnRole: " + arnRole);

        ListMetricsRequest listMetricsRequest = ListMetricsRequest.builder()
                .namespace("EBS")
                .build();

        ListMetricsResponse response = cloudWatchClient.listMetrics(listMetricsRequest);

        System.out.println(response.toString());
    }

    public static CloudWatchImpl getInstance() {
        Injector injector = Guice.createInjector(new CloudwatchModule());
        return injector.getInstance(CloudWatchImpl.class);
    }

}
