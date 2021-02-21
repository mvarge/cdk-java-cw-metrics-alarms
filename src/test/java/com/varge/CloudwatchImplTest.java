package com.varge;

import com.varge.cloudwatch.CloudwatchImpl;
import org.junit.Test;
import org.mockito.Mockito;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.ListMetricsRequest;
import software.amazon.awssdk.services.cloudwatch.model.ListMetricsResponse;
import software.amazon.awssdk.services.cloudwatch.model.Metric;

public class CloudwatchImplTest {

    private final String arnRole = "somerole";

    @Test
    public void testListMetric() {
        CloudWatchClient cloudWatchClient = Mockito.mock(CloudWatchClient.class);
        Mockito.when(cloudWatchClient.listMetrics(ListMetricsRequest
                .builder()
                .namespace("EBS")
                .build())).thenReturn(ListMetricsResponse.builder()
                .metrics(Metric.builder()
                        .namespace("MOCK")
                        .metricName("MockMetric")
                        .metricName("MockMetric2")
                        .build())
                .build());

        CloudwatchImpl cloudwatch = new CloudwatchImpl(cloudWatchClient, arnRole);
        ListMetricsResponse listMetricsResponse = cloudwatch.listMetrics();

        assert(listMetricsResponse.hasMetrics());
    }

}
