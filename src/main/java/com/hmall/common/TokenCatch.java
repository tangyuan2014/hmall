package com.hmall.common;

import ch.qos.logback.classic.Logger;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TokenCatch {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TokenCatch.class);

    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    return "NULL";
                }
            });

    public static void setKey(String key, String value) {
        localCache.put(key, value);
    }

    public static String getKey(String key) {
        String value = null;
        try {
            value = localCache.get(key);
            if ("NULL".equals(value)) {
                return null;
            }
            return value;
        } catch (Exception exception) {
            logger.error("localCache get error", exception);
        }
        return null;

    }
}
