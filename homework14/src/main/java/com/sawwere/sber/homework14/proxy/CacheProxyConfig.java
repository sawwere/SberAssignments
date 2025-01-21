package com.sawwere.sber.homework14.proxy;

import com.sawwere.sber.homework14.storage.db.DBStorage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

@Builder
@Getter
@Setter
public class CacheProxyConfig {
    @Builder.Default
    private Path cacheDir = Path.of("./temp");
    @Builder.Default
    private Boolean shouldCleanOnStart = false;

    private DBStorage dbStorage;
}
