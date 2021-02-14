package com.varge;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.cloudwatch.*;
import software.amazon.awscdk.services.cloudwatch.actions.SnsAction;
import software.amazon.awscdk.services.sns.Topic;

import java.util.HashMap;

public class CloudWatchStack extends Stack {

    private final String NAMESPACE = "MyService";

    public CloudWatchStack(final Construct scope, final String id, StackProps props) {
        super(scope, id, props);

        Metric metric =
                Metric.Builder.create()
                        .metricName("MyMetric")
                        // The inline HashMap might be anti pattern in regular scenarios but for this
                        // dimension case is pretty straight forward.
                        .dimensions(new HashMap<String, String>() {{
                            put("Some", "Dimension");
                            put("Another", "Dimension");
                        }})
                        .namespace(NAMESPACE)
                        .build();

        Alarm alarm = metric.createAlarm(this, "DummyAlarm", new AlarmProps.Builder()
                .alarmDescription("My alarm description")
                .threshold(50)
                .comparisonOperator(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO_THRESHOLD)
                .evaluationPeriods(3)
                .datapointsToAlarm(3)
                .metric(metric)
                .build());

        Alarm invertedAlarm = Alarm.Builder.create(this, "NewDummyAlarm")
                .alarmName("A new dummy alarm")
                .datapointsToAlarm(3)
                .evaluationPeriods(3)
                .threshold(500)
                .comparisonOperator(ComparisonOperator.GREATER_THAN_THRESHOLD)
                .metric(Metric.Builder.create()
                        .namespace(NAMESPACE)
                        .metricName("MetricName")
                        .build())
                .treatMissingData(TreatMissingData.NOT_BREACHING)
                .actionsEnabled(true)
                .build();

        Topic alarmTopic = Topic.Builder.create(this, "AlarmNotification")
                .topicName("AlarmNotificationTopic")
                .build();

        alarm.addAlarmAction(new SnsAction(alarmTopic));
        invertedAlarm.addAlarmAction(new SnsAction(alarmTopic));

    }
}
