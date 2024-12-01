package com.sawwere.sber.homework5.calculator;

import com.sawwere.sber.homework5.calculator.Calculator;

public class CalculatorImpl implements Calculator {
    /**
     * Расчет факториала числа.
     *
     * @param number будет вычислен факториал этого числа
     */
    @Override
    public int calc(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Cannot calculate factorial for number less than 0.");
        }
        int result = 1;
        for (int i = 1; i <= number; i++) {
            result *= i;
        }
        return result;
    }
}
