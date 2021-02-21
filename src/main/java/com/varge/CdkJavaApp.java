package com.varge;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.varge.cloudwatch.CloudWatchImpl;
import com.varge.modules.CloudwatchModule;
import com.varge.utils.StsClientHelper;
import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.model.Credentials;

public class CdkJavaApp {

    public final static Region REGION = Region.EU_WEST_1;
    private static CloudWatchImpl cwExecutor;

    private static Environment envWithRegion(String account, String region) {
        account = (account == null) ? System.getenv("CDK_DEFAULT_ACCOUNT") : account;
        region = (region == null) ? System.getenv("CDK_DEFAULT_REGION") : region;

        StsClientHelper stsActivator = StsClientHelper.builder()
                .arnRole("arn:aws:iam::<account>:role/<RoleName>")
                .roleSessionName("session_x")
                .build();

        Credentials credentials = stsActivator.getStsCredentials();
        System.out.println(credentials.accessKeyId() + "\n" + credentials.secretAccessKey());

        return new Environment.Builder()
                .account(account)
                .region(region)
                .build();
    }

    private static void cloudwatchMetrics() {
        cwExecutor = CloudWatchImpl.getInstance();
        cwExecutor.listMetrics();
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
