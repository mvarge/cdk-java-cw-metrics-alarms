package com.varge;

import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.StackProps;

public class CdkJavaApp {

    private final static String REGION = "eu-west-1";

    private static Environment envWithRegion(String account, String region) {
        account = (account == null) ? System.getenv("CDK_DEFAULT_ACCOUNT") : account;
        region = (region == null) ? System.getenv("CDK_DEFAULT_REGION") : region;

        return new Environment.Builder()
                .account(account)
                .region(region)
                .build();
    }

    public static void main(final String[] args) {
        App app = new App();

        new CdkJavaStack(app, "CdkJavaStack");

        new CloudWatchStack(app, "CloudWatchStack", StackProps.builder()
                .env(envWithRegion(null, REGION))
                .build());

        app.synth();
    }
}
