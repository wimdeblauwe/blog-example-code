package com.wimdeblauwe.examples.htmxiotdemo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.random.RandomGenerator;

@Service
public class IotDeviceService {
    private static final RandomGenerator RANDOM_GENERATOR = RandomGenerator.getDefault();

    private final List<IotDevice> devices = List.of(new IotDevice(1L, "QWER-001"),
                                                    new IotDevice(2L, "ASDF-342"),
                                                    new IotDevice(3L, "ZXCV-976"),
                                                    new IotDevice(4L, "TYUI-253"),
                                                    new IotDevice(5L, "GHJK-975"),
                                                    new IotDevice(6L, "VBNM-225")
    );

    public List<IotDevice> getDevices() {
        return devices;
    }

    public double getTemperature(IotDevice device) {
        try {
            Thread.sleep(RANDOM_GENERATOR.nextLong(10, 800));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return RANDOM_GENERATOR.nextDouble(20, 40);
    }

    public IotDevice getDevice(long id) {
        return devices.stream()
                      .filter(iotDevice -> iotDevice.id().equals(id))
                      .findAny()
                      .orElseThrow();
    }
}
