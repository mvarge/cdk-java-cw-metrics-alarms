package com.varge;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;


public class CdkJavaStack extends Stack {
    public CdkJavaStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    private enum Test {
        SOMETHING,
        SOMETHING_NEW,
        ANOTHER_SOMETHING
    }

    public CdkJavaStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // The code that defines your stack goes here

        Test t = Test.SOMETHING;

    }
}
