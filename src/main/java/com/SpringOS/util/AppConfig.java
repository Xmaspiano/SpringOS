package com.SpringOS.util;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * Created by AlbertXmas on 16/9/7.
 */
//@Configuration
//@ComponentScan(basePackages = "com.SpringOS.*")
//@EnableCaching(proxyTargetClass = true)
public class AppConfig implements CachingConfigurer {
//    @Bean
    @Override
    public CacheManager cacheManager() {

        try {
            net.sf.ehcache.CacheManager ehcacheCacheManager
                    = new net.sf.ehcache.CacheManager(new ClassPathResource("ehcache/ehcache.xml").getInputStream());

            EhCacheCacheManager cacheCacheManager = new EhCacheCacheManager(ehcacheCacheManager);
            return cacheCacheManager;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Return the {@link CacheResolver} bean to use to resolve regular caches for
     * annotation-driven cache management. This is an alternative and more powerful
     * option of specifying the {@link CacheManager} to use.
     * <p>If both a {@link #cacheManager()} and {@link #cacheResolver()} are set, the
     * cache manager is ignored.
     * <p>Implementations must explicitly declare
     * {@link Bean @Bean}, e.g.
     * <pre class="code">
     * &#064;Configuration
     * &#064;EnableCaching
     * public class AppConfig extends CachingConfigurerSupport {
     * &#064;Bean // important!
     * &#064;Override
     * public CacheResolver cacheResolver() {
     * // configure and return CacheResolver instance
     * }
     * // ...
     * }
     * </pre>
     * See {@link EnableCaching} for more complete examples.
     */
    @Override
    public CacheResolver cacheResolver() {
        return null;
    }

//    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    /**
     * Return the {@link CacheErrorHandler} to use to handle cache-related errors.
     * <p>By default,{@link SimpleCacheErrorHandler}
     * is used and simply throws the exception back at the client.
     * <p>Implementations must explicitly declare
     * {@link Bean @Bean}, e.g.
     * <pre class="code">
     * &#064;Configuration
     * &#064;EnableCaching
     * public class AppConfig extends CachingConfigurerSupport {
     * &#064;Bean // important!
     * &#064;Override
     * public CacheErrorHandler errorHandler() {
     * // configure and return CacheErrorHandler instance
     * }
     * // ...
     * }
     * </pre>
     * See @{@link EnableCaching} for more complete examples.
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }
}
