package com.sawwere.sber.homework8.cache.proxy;

import lombok.*;

import java.nio.file.Path;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CacheProxyConfig {
    @Builder.Default
    private Path cacheDir = Path.of("./temp");
    @Builder.Default
    private Boolean shouldCleanOnExit = false;
}
