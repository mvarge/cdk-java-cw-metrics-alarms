package com.varge.model;

import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.services.cloudwatch.AlarmActionConfig;
import software.amazon.awscdk.services.cloudwatch.IAlarm;
import software.amazon.awscdk.services.cloudwatch.IAlarmAction;

public class AlarmAction implements IAlarmAction {
    @Override
    public @NotNull AlarmActionConfig bind(@NotNull Construct construct, @NotNull IAlarm iAlarm) {
        return null;
    }
}
