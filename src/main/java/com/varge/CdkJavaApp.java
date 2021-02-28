package com.varge;

import com.varge.cloudwatch.CloudwatchImpl;
import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awssdk.regions.Region;

public class CdkJavaApp {

    public final static Region REGION = Region.EU_WEST_1;
    private static CloudwatchImpl cwExecutor;

    private static Environment envWithRegion(String account, String region) {
        account = (account == null) ? System.getenv("CDK_DEFAULT_ACCOUNT") : account;
        region = (region == null) ? System.getenv("CDK_DEFAULT_REGION") : region;

        return new Environment.Builder()
                .account(account)
                .region(region)
                .build();
    }

    private static void cloudwatchMetrics() {
        cwExecutor = CloudwatchImpl.getInstance();
        cwExecutor.listMetrics();
        cwExecutor.sendMetric();
    }

    public static void main(final String[] args) {

        cloudwatchMetrics();

        App app = new App();

        new CdkJavaStack(app, "CdkJavaStack");

        new CloudWatchStack(app, "CloudWatchStack", StackProps.builder()
                .env(envWithRegion(null, REGION.toString()))
                .build());

        app.synth();
    }
}
