package com.wimdeblauwe.examples.requestparametersvalidation;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@FromMoreRecentThenTo
public record GetTaskRequestParameters(@Past
                                       @NotNull
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                       LocalDate from,
                                       @PastOrPresent
                                       @NotNull
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                       LocalDate to) {
}
