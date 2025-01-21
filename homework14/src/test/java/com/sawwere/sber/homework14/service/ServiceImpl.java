package com.sawwere.sber.homework14.service;

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

        return time.toString();
    }

    @Override
    public String runInDatabase(Integer time, String name) {
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
}
