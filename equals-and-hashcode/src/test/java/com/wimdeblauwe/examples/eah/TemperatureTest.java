package com.wimdeblauwe.examples.eah;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TemperatureTest {

    @Test
    void testEqualTemperature() {
        Temperature temperature1 = new Temperature(37.0, Temperature.Unit.CELCIUS);
        Temperature temperature2 = new Temperature(37.0, Temperature.Unit.CELCIUS);

        boolean equal = temperature1.equals(temperature2);
        assertTrue(equal);
    }

    @Test
    void testHashCodeForEqualObjects() {
        Temperature temperature1 = new Temperature(37.0, Temperature.Unit.CELCIUS);
        Temperature temperature2 = new Temperature(37.0, Temperature.Unit.CELCIUS);

        int hashCode1 = temperature1.hashCode();
        int hashCode2 = temperature2.hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Temperature.class).verify();
    }
}
