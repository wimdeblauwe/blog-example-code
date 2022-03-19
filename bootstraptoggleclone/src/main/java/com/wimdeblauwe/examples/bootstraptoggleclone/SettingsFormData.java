package com.wimdeblauwe.examples.bootstraptoggleclone;

import java.util.StringJoiner;

public class SettingsFormData {
    private boolean notifyViaEmail;
    private boolean notifyViaSms;

    public boolean isNotifyViaEmail() {
        return notifyViaEmail;
    }

    public void setNotifyViaEmail(boolean notifyViaEmail) {
        this.notifyViaEmail = notifyViaEmail;
    }

    public boolean isNotifyViaSms() {
        return notifyViaSms;
    }

    public void setNotifyViaSms(boolean notifyViaSms) {
        this.notifyViaSms = notifyViaSms;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SettingsFormData.class.getSimpleName() + "[", "]")
                .add("notifyViaEmail=" + notifyViaEmail)
                .add("notifyViaSms=" + notifyViaSms)
                .toString();
    }
}
