package com.sawwere.sber.homework5.helper;

public class GrandChild extends ChildString {
    private final String WEEKEND;
    private Integer d;

    public GrandChild(Integer a, Integer b, String c, String privateFieldValue) {
        super(a, b, c);
        WEEKEND = privateFieldValue;
    }

    public Integer getD() {
        return d;
    }

    public void setD(Integer d) {
        this.d = d;
    }
}
