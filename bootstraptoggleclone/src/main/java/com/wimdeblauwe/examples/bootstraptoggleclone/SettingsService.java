package com.wimdeblauwe.examples.bootstraptoggleclone;

import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    private Settings settings = new Settings(false, false);

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
