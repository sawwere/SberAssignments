package com.sawwere.sber.homework7;

public class PluginLoadException extends Exception {
    public PluginLoadException(String reason, String message) {
        super(String.format("Ошибка загрузки плагина. Причина: %sСообщение ошибки: %s", reason, message));
    }
}
