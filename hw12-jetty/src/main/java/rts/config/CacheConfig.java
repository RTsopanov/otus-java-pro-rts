package rts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rts.cachehw.HwCache;
import rts.cachehw.MyCache;
import rts.dto.ClientDto;

@Configuration
public class CacheConfig {
    @Bean
    public HwCache<Long, ClientDto> clientCache() {
        return new MyCache<>();
    }
}