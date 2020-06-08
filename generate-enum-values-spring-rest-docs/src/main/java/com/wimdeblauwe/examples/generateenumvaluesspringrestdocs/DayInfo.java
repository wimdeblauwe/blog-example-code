package com.wimdeblauwe.examples.generateenumvaluesspringrestdocs;

import java.time.LocalDate;

class DayInfo {
    private final LocalDate date;
    private final Season season;

    public DayInfo(LocalDate date, Season season) {
        this.date = date;
        this.season = season;
    }

    public LocalDate getDate() {
        return date;
    }

    public Season getSeason() {
        return season;
    }
}
