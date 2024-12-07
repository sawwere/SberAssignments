package com.sawwere.sber.homework7.encrypted;


public interface Encoder {
    byte[] encode(byte[] bytes, String key);

    byte[] decode(byte[] bytes, String key);
}
