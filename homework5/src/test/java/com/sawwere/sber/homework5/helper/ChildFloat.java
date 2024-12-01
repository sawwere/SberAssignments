package com.sawwere.sber.homework5.helper;

public class ChildFloat extends Parent {
    protected Float c;

    public ChildFloat(Integer a, Integer b, Float c) {
        super(a, b);
        setC(c);
    }

    public Float getC() {
        return c;
    }

    public void setC(Float c) {
        this.c = c;
    }
}
