package com.myorg;

import software.amazon.awscdk.core.App;

import java.util.Arrays;

public class CdkJavaApp {
    public static void main(final String[] args) {
        App app = new App();

        new CdkJavaStack(app, "CdkJavaStack");

        app.synth();
    }
}
