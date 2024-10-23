package com.wora.common.application.validation.validator;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateValidatorTest {

   @Test
   void isDateBetween_ShouldReturnFalse_WhenGivenPastDate() {
       LocalDate givenDate = LocalDate.now().minusDays(21);
       LocalDate before = LocalDate.now();
       LocalDate after = LocalDate.now().plusDays(20);

       boolean actual = DateValidator.isDateBetween(givenDate, before, after);

       assertFalse(actual);
   }

   @Test
    void isDateBetween_ShouldReturnFalse_WhenGivenNotValidDate() {
       LocalDate givenDate = LocalDate.now().plusDays(30);
       LocalDate before = LocalDate.now();
       LocalDate after = LocalDate.now().plusDays(20);

       boolean actual = DateValidator.isDateBetween(givenDate, before, after);

       assertFalse(actual);
   }

    @Test
    void isDateBetween_ShouldReturnTrue_WhenGivenNotValidDate() {
        LocalDate givenDate = LocalDate.now().plusDays(10);
        LocalDate before = LocalDate.now();
        LocalDate after = LocalDate.now().plusDays(20);

        boolean actual = DateValidator.isDateBetween(givenDate, before, after);

        assertTrue(actual);
    }
}