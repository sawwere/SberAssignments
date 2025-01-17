package com.sawwere.sber.homework12.proxy;

import java.nio.file.Path;


public class CacheProxyConfig {
    private Path cacheDir = Path.of("./temp");
    private boolean shouldCleanOnExit = false;

    public static CacheProxyConfigBuilder builder() {
        return new CacheProxyConfigBuilder(new CacheProxyConfig());
    }

    public static class CacheProxyConfigBuilder {
        private final CacheProxyConfig object;

        public CacheProxyConfigBuilder(CacheProxyConfig object) {
            this.object = object;
        }

        public CacheProxyConfigBuilder cacheDir(Path cacheDir) {
            object.setCacheDir(cacheDir);
            return this;
        }

        public CacheProxyConfigBuilder shouldCleanOnExit(boolean shouldCleanOnExit) {
            object.setShouldCleanOnExit(shouldCleanOnExit);
            return this;
        }

        public CacheProxyConfig build() {
            return object;
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
