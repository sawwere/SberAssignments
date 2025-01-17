package com.sawwere.sber.homework12.service;

import java.util.ArrayList;
import java.util.List;

public class ServiceImpl implements Service {
    @Override
    public String run(Integer time, String name) {
        System.out.println("Enter run(%d, %s)".formatted(time, name));
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "Completed %s in %d seconds".formatted(name, time);
    }

    @Override
    public String runWithAnotherName(Integer time, String name) {
        System.out.println("Enter runWithAnotherName(%d, %s)".formatted(time, name));
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "Completed %s in %d seconds".formatted(name, time);
    }

    @Override
    public String runInFile(Integer time, String name) {
        return run(time, name);
    }

    @Override
    public String runInZip(Integer time, String name) {
        return run(time, name);
    }

    @Override
    public List<Integer> getListOfSize(Integer size) {
        List<Integer> res = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            res.add(i);
        }
        return res;
    }

    @Override
    public NonSerializable runNonSerializable(Integer time) {
       System.out.println("time " + time);
       return new NonSerializable();
    }
}
