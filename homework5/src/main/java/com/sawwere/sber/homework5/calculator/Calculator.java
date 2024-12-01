package com.sawwere.sber.homework5.calculator;

import com.sawwere.sber.homework5.metric.Metric;
import com.sawwere.sber.homework5.cache.Cache;

public interface Calculator{
    /**
     * Расчет факториала числа.
     * @param number будет вычислен факториал этого числа
     */
    @Metric
    @Cache
    int calc (int number);
}
