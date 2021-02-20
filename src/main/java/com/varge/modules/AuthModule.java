package com.varge.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.varge.CdkJavaApp;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;


public class AuthModule extends AbstractModule {

    @Override
    public void configure() {
    }

    @Provides
    @Named(value = "arnRole")
    public String getArnRole() {
        return "arn:aws:iam::<account>:role/<RoleName>";
    }

    @Provides
    @Named(value = "roleSessionName")
    public String getRoleSessionName() {
        return "session_x";
    }

    @Provides
    public StsClient getStsClient() {
        return StsClient.builder()
                .region(CdkJavaApp.REGION)
                .build();
    }

    @Provides
    public StsAssumeRoleCredentialsProvider getCredentialsProvider(StsClient stsClient,
                               @Named("arnRole") String arnRole,
                               @Named("roleSessionName") String roleSessionName) {
        AssumeRoleRequest assumeRoleRequest = AssumeRoleRequest.builder()
                .roleArn(arnRole)
                .roleSessionName(roleSessionName)
                .build();

        // To read credentials one can read AssumeRoleResponse
        // AssumeRoleResponse roleResponse = stsClient.assumeRole(assumeRoleRequest);

        return StsAssumeRoleCredentialsProvider.builder()
                .stsClient(stsClient)
                .refreshRequest(assumeRoleRequest)
                .build();
    }

}
