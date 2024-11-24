package com.sawwere;

class Apple extends Fruit {
    public Apple(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "I am an Apple %s!!".formatted(getId());
    }
}
