package com.wimdeblauwe.examples.htmxsse;

public interface ProgressListener {
    void onProgress(int value);

    void onCompletion();
}
