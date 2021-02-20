package com.varge.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider;

public class CloudwatchModule extends AbstractModule {

    @Override
    public void configure() {
        install(new AuthModule());
    }

    @Provides
    public CloudWatchClient getCloudwatchClient(StsAssumeRoleCredentialsProvider stsAssumeRoleCredentialsProvider) {
        return CloudWatchClient.builder()
                .credentialsProvider(stsAssumeRoleCredentialsProvider)
                .build();
    }

}
