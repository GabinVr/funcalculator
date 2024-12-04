package com.example.funcalculator;

import com.example.funcalculator.model.equation.EquationScanner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

import android.util.Log;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;

import java.util.Arrays;

public class TestEquationScanner {
    private static MockedStatic<Log> logMockedStatic;

    @BeforeClass
    public static void setUp() {
        logMockedStatic = mockStatic(Log.class);
        logMockedStatic.when(() -> Log.d(anyString(), anyString())).thenAnswer(invocation -> {
            System.out.println(Arrays.toString(invocation.getArguments()));
            return null;
        });
    }

    @AfterClass
    public static void tearDown() {
        if (logMockedStatic != null) {
            logMockedStatic.close();
        }
    }

    @Test
    public void testIsEquationValid() {
        // Test valid equations
        String equation1 = "2+3=5";
        String equation2 = "10/2=5";
        String equation3 = "3*3=9";
        String equation4 = "4-1=3";

        assertTrue("The equation should be valid: " + equation1, EquationScanner.isEquationValid(equation1));
        assertTrue("The equation should be valid: " + equation2, EquationScanner.isEquationValid(equation2));
        assertTrue("The equation should be valid: " + equation3, EquationScanner.isEquationValid(equation3));
        assertTrue("The equation should be valid: " + equation4, EquationScanner.isEquationValid(equation4));
    }

    @Test
    public void testIsEquationInvalid() {
        String equation1 = "2+3=6";
        String equation2 = "10/2=4";
        String equation3 = "3*3=8";
        String equation4 = "4-1=5";
        assertFalse("The equation should be invalid: " + equation1, EquationScanner.isEquationValid(equation1));
        assertFalse("The equation should be invalid: " + equation2, EquationScanner.isEquationValid(equation2));
        assertFalse("The equation should be invalid: " + equation3, EquationScanner.isEquationValid(equation3));
        assertFalse("The equation should be invalid: " + equation4, EquationScanner.isEquationValid(equation4));
    }

    @Test
    public void testEmptyAndNullInput() {
        String equation1 = null;
        String equation2 = "";
        String equation3 = "3+2=";
        assertFalse("Null equation should be invalid.", EquationScanner.isEquationValid(equation1));
        assertFalse("Empty equation should be invalid.", EquationScanner.isEquationValid(equation2));
        assertFalse("Incomplete equation should be invalid.", EquationScanner.isEquationValid(equation3));
    }

    @Test
    public void testComplexEquations() {
        String equation1 = "(2+3)*(4-1)=15";
        String equation2 = "2*(5+5)/10=2";
        String equation3 = "(3*3) - (5/1) = 4";
        assertTrue("The equation should be valid: " + equation1, EquationScanner.isEquationValid(equation1));
        assertTrue("The equation should be valid: " + equation2, EquationScanner.isEquationValid(equation2));
        assertTrue("The equation should be valid: " + equation3, EquationScanner.isEquationValid(equation3));
    }
}
