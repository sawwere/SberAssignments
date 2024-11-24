package com.sawwere;

import java.util.Objects;

class Fruit {
    private String name;

    public Fruit(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "I am a Fruit %s!!".formatted(getId());
    }

    public String getId() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fruit fruit = (Fruit) o;
        return name == fruit.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
