package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the exponentiation function in HelloApplication.
 * This class uses JUnit 5 to test various scenarios.
 */
class HelloApplicationTest {

    // --- Basic Requirement Tests ---

    @Test
    @DisplayName("REQ-EXP-004: Any base to the power of 0 should be 1")
    void testExponentIsZero() throws ExponentiationException {
        // Tests x^0 = 1
        assertEquals(1, HelloApplication.exponentiation(5, 0), "5^0 should be 1");
        assertEquals(1, HelloApplication.exponentiation(-10, 0), "-10^0 should be 1");
        assertEquals(1, HelloApplication.exponentiation(0, 0), "0^0 is conventionally 1 in this context");
    }

    @Test
    @DisplayName("REQ-EXP-005: Any base to the power of 1 should be the base itself")
    void testExponentIsOne() throws ExponentiationException {
        // Tests x^1 = x
        assertEquals(7, HelloApplication.exponentiation(7, 1), "7^1 should be 7");
        assertEquals(-3, HelloApplication.exponentiation(-3, 1), "-3^1 should be -3");
        assertEquals(0, HelloApplication.exponentiation(0, 1), "0^1 should be 0");
    }

    @Test
    @DisplayName("REQ-EXP-006: Base of 1 to any power should be 1")
    void testBaseIsOne() throws ExponentiationException {
        // Tests 1^y = 1
        assertEquals(1, HelloApplication.exponentiation(1, 100), "1^100 should be 1");
        assertEquals(1, HelloApplication.exponentiation(1, -5), "1^-5 should be 1");
        assertEquals(1, HelloApplication.exponentiation(1, 0), "1^0 should be 1");
    }

    @Test
    @DisplayName("REQ-EXP-007: Base of 0 to any positive power should be 0")
    void testBaseIsZeroPositiveExponent() throws ExponentiationException {
        // Tests 0^y = 0 for y > 0
        assertEquals(0, HelloApplication.exponentiation(0, 5), "0^5 should be 0");
    }

    // --- Exception and Error Handling Tests ---

    @Test
    @DisplayName("REQ-EXP-008: Base of 0 to a negative power should throw ExponentiationException")
    void testBaseIsZeroNegativeExponentThrowsException() {
        // Tests 0^y for y < 0, which is division by zero
        Exception exception = assertThrows(ExponentiationException.class, () -> {
            HelloApplication.exponentiation(0, -2);
        });
        assertEquals("Division by zero: 0^y where y < 0 is undefined.", exception.getMessage());
    }

    @Test
    @DisplayName("Overflow: Result exceeding Long.MAX_VALUE should throw OverflowException")
    void testPositiveOverflowThrowsException() {
        // This calculation will quickly exceed Long.MAX_VALUE
        assertThrows(OverflowException.class, () -> {
            HelloApplication.exponentiation(Integer.MAX_VALUE, 2);
        }, "Squaring Integer.MAX_VALUE should cause an overflow");

        assertThrows(OverflowException.class, () -> {
            HelloApplication.exponentiation(99999, 4);
        }, "99999^4 should cause an overflow");
    }

    @Test
    @DisplayName("Overflow: Intermediate value exceeding Long.MAX_VALUE should throw OverflowException")
    void testIntermediateOverflowThrowsException() {
        // A case where an intermediate step (base * base) overflows before the final result.
        // 3037000500 is approx sqrt(Long.MAX_VALUE), so squaring it will overflow.
        assertThrows(OverflowException.class, () -> {
            long exponentiation = HelloApplication.exponentiation(30370005, 2);
        }, "Squaring a large number should cause an intermediate overflow");
    }


    // --- General Functionality Tests ---

    @Test
    @DisplayName("Positive base and positive exponent")
    void testPositiveBasePositiveExponent() throws ExponentiationException {
        assertEquals(1024, HelloApplication.exponentiation(2, 10), "2^10 should be 1024");
        assertEquals(243, HelloApplication.exponentiation(3, 5), "3^5 should be 243");
    }

    @Test
    @DisplayName("Negative base with even exponent should be positive")
    void testNegativeBaseEvenExponent() throws ExponentiationException {
        assertEquals(16, HelloApplication.exponentiation(-2, 4), "-2^4 should be 16");
        assertEquals(100, HelloApplication.exponentiation(-10, 2), "-10^2 should be 100");
    }

    @Test
    @DisplayName("Negative base with odd exponent should be negative")
    void testNegativeBaseOddExponent() throws ExponentiationException {
        assertEquals(-8, HelloApplication.exponentiation(-2, 3), "-2^3 should be -8");
        assertEquals(-1, HelloApplication.exponentiation(-1, 99), "-1^99 should be -1");
    }

    @Test
    @DisplayName("Negative exponent should result in 0 for integers (except base -1)")
    void testNegativeExponent() throws ExponentiationException {
        // For integer arithmetic, x^-y where x is not 1 or -1 results in a fraction that truncates to 0.
        assertEquals(0, HelloApplication.exponentiation(2, -3), "2^-3 should truncate to 0");
        assertEquals(0, HelloApplication.exponentiation(-4, -2), "-4^-2 should truncate to 0");
    }

    @Test
    @DisplayName("Base of -1 with negative exponent should alternate")
    void testBaseIsMinusOneNegativeExponent() throws ExponentiationException {
        assertEquals(1, HelloApplication.exponentiation(-1, -2), "(-1)^-2 should be 1");
        assertEquals(-1, HelloApplication.exponentiation(-1, -3), "(-1)^-3 should be -1");
    }
}
