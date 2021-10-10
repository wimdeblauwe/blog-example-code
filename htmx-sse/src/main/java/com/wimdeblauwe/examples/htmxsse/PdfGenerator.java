package com.wimdeblauwe.examples.htmxsse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

@Component
public class PdfGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(PdfGenerator.class);

    private final RandomGenerator randomGenerator = RandomGeneratorFactory.getDefault().create();

    public void generatePdf(ProgressListener listener) {
        LOGGER.info("Generating PDF...");
        int progress = 0;
        listener.onProgress(progress);
        do {
            sleep();
            progress += randomGenerator.nextInt(10);
            LOGGER.info("Progress: {} ", progress);
            listener.onProgress(progress);
        } while (progress < 100);
        LOGGER.info("Done!");
        listener.onCompletion();
    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
    }
}
