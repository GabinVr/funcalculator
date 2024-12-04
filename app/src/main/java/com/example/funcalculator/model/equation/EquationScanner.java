package com.example.funcalculator.model.equation;

import android.util.Log;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class EquationScanner {
    private static int _equalSignIndex = -1;
    private static final double EPSILON = 1e-3;

    public static boolean isEquationValid(String equation) {
        if (equation == null || equation.isEmpty()) {
            return false;
        }

        if (!hasOneEqualSign(equation)) {
            return false;
        }

        String leftPart = equation.substring(0, _equalSignIndex);
        String rightPart = equation.substring(_equalSignIndex + 1);
        try {
            double leftPartValue = evaluate(leftPart);
            double rightPartValue = evaluate(rightPart);
            return Math.abs(leftPartValue - rightPartValue) < EPSILON;
        } catch (Exception e) {
            Log.d("EquationScanner", "Invalid equation: " + e.getMessage());
            return false;
        }
    }

    public static boolean hasOneEqualSign(String equation) {
        if (equation == null || equation.isEmpty()) {
            return false;
        }
        int equalSignIndex = equation.indexOf('=');
        if (equalSignIndex == -1 || equation.indexOf('=', equalSignIndex + 1) != -1) {
            return false;
        }
        _equalSignIndex = equalSignIndex;
        return true;
    }

    private static double evaluate(String equationPart) {
        if (equationPart == null || equationPart.isEmpty()) {
            throw new IllegalArgumentException("Invalid equation part");
        }

        // Use Rhino to evaluate the JavaScript expression
        Context rhino = Context.enter();
        try {
            rhino.setOptimizationLevel(-1); // Disable optimization for compatibility
            Scriptable scope = rhino.initStandardObjects();
            Object result = rhino.evaluateString(scope, equationPart, "JavaScript", 1, null);

            if (result instanceof Number) {
                return ((Number) result).doubleValue();
            } else {
                throw new IllegalArgumentException("Invalid equation part result");
            }
        } finally {
            Context.exit();
        }
    }
}
