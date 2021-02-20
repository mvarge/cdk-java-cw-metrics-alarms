package com.varge.utils;

import com.varge.CdkJavaApp;
import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
import software.amazon.awssdk.services.sts.model.AssumeRoleResponse;
import software.amazon.awssdk.services.sts.model.Credentials;

@Builder
@Data
public class StsClientHelper {

    private String arnRole;
    private String roleSessionName;

    public Credentials getStsCredentials() {
        StsClient stsClient = StsClient.builder()
                .region(CdkJavaApp.REGION)
                .build();

        AssumeRoleRequest assumeRoleRequest = AssumeRoleRequest.builder()
                .roleArn(arnRole)
                .roleSessionName(roleSessionName)
                .build();

        AssumeRoleResponse roleResponse = stsClient.assumeRole(assumeRoleRequest);
        Credentials credentials = roleResponse.credentials();

        return credentials;
    }

}
