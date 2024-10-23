package com.wora.common.application.validation.validator;

import java.time.LocalDate;

public class DateValidator {

    public static boolean isDateBetween(LocalDate givenDate, LocalDate before, LocalDate after) {
        if (givenDate == null || before == null || after == null) return false;
        return !givenDate.isBefore(before) && !givenDate.isAfter(after);
    }
}
