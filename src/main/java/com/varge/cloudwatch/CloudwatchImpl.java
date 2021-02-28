package com.varge.cloudwatch;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.varge.modules.CloudwatchModule;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.*;

public class CloudwatchImpl {

    private final CloudWatchClient cloudWatchClient;
    private final String arnRole;

    @Inject
    public CloudwatchImpl(CloudWatchClient cloudWatchClient,
                          @Named("arnRole") String arnRole) {
        this.arnRole = arnRole;
        this.cloudWatchClient = cloudWatchClient;
    }

    public ListMetricsResponse listMetrics() {
        System.out.println("arnRole: " + arnRole);

        ListMetricsRequest listMetricsRequest = ListMetricsRequest.builder()
                .namespace("EBS")
                .build();

        ListMetricsResponse response = cloudWatchClient.listMetrics(listMetricsRequest);

        System.out.println(response.toString());
        return response;
    }

    public void sendMetric() {
        PutMetricDataResponse response =
                cloudWatchClient.putMetricData(PutMetricDataRequest.builder()
                .namespace("VargemTest")
                .metricData(MetricDatum.builder()
                        .metricName("MissingData")
                        .value(2.0)
                        .build())
                .build());

        System.out.println(response.toString());
    }

    public static CloudwatchImpl getInstance() {
        Injector injector = Guice.createInjector(new CloudwatchModule());
        return injector.getInstance(CloudwatchImpl.class);
    }

}
