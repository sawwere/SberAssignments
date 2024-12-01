package com.sawwere.sber.homework5;

import com.sawwere.sber.homework5.calculator.Calculator;
import com.sawwere.sber.homework5.calculator.CalculatorImpl;
import com.sawwere.sber.homework5.helper.ChildFloat;
import com.sawwere.sber.homework5.helper.ChildString;
import com.sawwere.sber.homework5.helper.GrandChild;
import com.sawwere.sber.homework5.helper.Parent;
import com.sawwere.sber.homework5.metric.PerformanceProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class HomeworkTest {
    @Test
    public void task1() {
        CalculatorImpl calculator = new CalculatorImpl();
        Assertions.assertEquals(1, calculator.calc(0));
        Assertions.assertEquals(1, calculator.calc(1));
        Assertions.assertEquals(2, calculator.calc(2));
        Assertions.assertEquals(120, calculator.calc(5));
    }

    @Test
    public void task2() {
        Class<GrandChild> c = GrandChild.class;
        ReflectionUtils.printAllMethods(c);
    }

    @Test
    public void task3() {
        Class<ChildString> c = ChildString.class;
        for (Method getter :ReflectionUtils.getAllGetters(c)) {
            System.out.println(getter);
        }
    }

    @Test
    public void task4() {
        Parent p = new Parent(0 ,14);
        GrandChild c = new GrandChild(1, 2, "some string", "invalid filed name");
        Assertions.assertTrue(ReflectionUtils.checkStringConstantsEqualsNames(p));
        Assertions.assertFalse(ReflectionUtils.checkStringConstantsEqualsNames(c));
    }

    @Test
    public void task6() {
        Calculator calc = new CalculatorImpl();
        Calculator proxyCalc = (Calculator) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                calc.getClass().getInterfaces(),
                new PerformanceProxy(calc)
        );
        proxyCalc.calc(12);
        proxyCalc.calc(1);
    }

    @Test
    public void task6WithoutAnnotation() {
        List<String> underTest = new ArrayList<>();
        List<String> proxy = (List<String>) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                underTest.getClass().getInterfaces(),
                new PerformanceProxy(underTest)
        );
        // ни один из методов не помечен аннотацией Metric - вывода в консоль быть не должно
        proxy.add("test");
    }

    @Test
    public void task7()  {
        ChildString from = new ChildString(1,2,"Привет");
        ChildFloat to = new ChildFloat(0,1000,66.13f);

        BeanUtils.assign(to, from);

        Assertions.assertEquals(from.getA(), to.getA());
        Assertions.assertEquals(from.getB(), to.getB());
    }
}
