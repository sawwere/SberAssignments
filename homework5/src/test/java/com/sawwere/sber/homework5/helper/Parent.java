package com.sawwere.sber.homework5.helper;

public class Parent {
    public static final String FINAL_FIELD = "FINAL_FIELD";

    private Integer a;
    private Integer b;
    public Parent(Integer a, Integer b) {

        this.a = a;
        this.b = b;
    }

    public Integer getA() {
        return a;
    }

    public Integer getB() {
        return b;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    private void privateMethod() {
        System.out.println("I am private method");
    }
}
