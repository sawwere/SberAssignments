package com.sawwere.sber.homework7.encrypted;


public class SecretEncoder implements Encoder {
    @Override
    public byte[] encode(byte[] bytes, String key) {
        byte[] result = new byte[bytes.length];
        byte[] keyBytes = key.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            result[i] = (byte) (bytes[i] + keyBytes[i % key.length()]);
        }
        return result;
    }

    @Override
    public byte[] decode(byte[] bytes, String key) {
        byte[] result = new byte[bytes.length];
        byte[] keyBytes = key.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            result[i] = (byte) (bytes[i] - keyBytes[i % key.length()]);
        }
        return result;
    }
}
