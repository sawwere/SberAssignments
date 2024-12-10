package com.sawwere.sber.homework7;

public class Main {
    public static void main(String[] args) {
        System.out.println("Задание №1 PluginManager");
        PluginManager pluginManager = new PluginManager("pluginsForManager/target");

        try {
            Plugin plugin = pluginManager.load("homework7-pluginsForManager-1.0-SNAPSHOT.jar",
                    "com.sawwere.sber.homework7.MyPlugin1");
            plugin.doUsefully();
        } catch (PluginLoadException exception) {
            exception.printStackTrace();
        }
    }
}