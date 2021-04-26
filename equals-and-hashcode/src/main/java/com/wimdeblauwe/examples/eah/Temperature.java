package com.wimdeblauwe.examples.eah;

import java.util.Objects;

public final class Temperature {
    private final double value;
    private final Unit unit;

    public Temperature(double value,
                       Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public Unit getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Temperature that = (Temperature) o;
        return Double.compare(that.value, value) == 0 && unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }

    public static Temperature ofKelvin(double value) {
        return new Temperature(value, Unit.KELVIN);
    }

    enum Unit {
        KELVIN, CELCIUS, FAHRENHEIT;
    }
}
