package com.sawwere.sber.homework5.helper;

public class ChildString extends Parent{
    protected String c;

    public ChildString(Integer a, Integer b, String c) {
        super(a, b);
        setC(c);
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }
}
