package com.sawwere.sber.homework12.proxy;

import java.nio.file.Path;


public class CacheProxyConfig {
    private Path cacheDir = Path.of("./temp");
    private boolean shouldCleanOnExit = false;

    public CacheProxyConfig(Path cacheDir, Boolean shouldCleanOnExit) {
        if (cacheDir != null) {
                this.cacheDir = cacheDir;
        }
        if (shouldCleanOnExit != null) {
            this.shouldCleanOnExit = shouldCleanOnExit;
        }
    }

    public Path getCacheDir() {
        return cacheDir;
    }

    public void setCacheDir(Path cacheDir) {
        this.cacheDir = cacheDir;
    }

    public Boolean getShouldCleanOnExit() {
        return shouldCleanOnExit;
    }

    public void setShouldCleanOnExit(Boolean shouldCleanOnExit) {
        this.shouldCleanOnExit = shouldCleanOnExit;
    }
}
