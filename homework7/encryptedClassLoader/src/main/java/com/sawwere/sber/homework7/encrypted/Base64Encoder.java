package com.sawwere.sber.homework7.encrypted;

import java.util.Base64;

public class Base64Encoder implements Encoder {
    @Override
    public byte[] encode(byte[] bytes, String key) {
        return Base64.getEncoder().encode(bytes);
    }

    @Override
    public byte[] decode(byte[] bytes, String key) {
        return Base64.getDecoder().decode(bytes);
    }
}
