package com.varge;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.cloudwatch.AlarmProps;
import software.amazon.awscdk.services.cloudwatch.Metric;

import java.util.HashMap;

public class CloudWatchStack extends Stack {

    private final String NAMESPACE = "MyService";

    public CloudWatchStack(final Construct scope, final String id, StackProps props) {
        super(scope, id, props);

        Metric metric =
                Metric.Builder.create()
                        .metricName("DummyMetric")
                        .dimensions(new HashMap<String, String>() {{
                            put("Some", "Dimension");
                        }})
                        .namespace(NAMESPACE)
                        .build();

        metric.createAlarm(this, "DummyAlarm", new AlarmProps.Builder()
                .alarmDescription("My alarm description")
                .threshold(50)
                .evaluationPeriods(3)
                .datapointsToAlarm(3)
                .metric(metric)
                .build());

    }
}
