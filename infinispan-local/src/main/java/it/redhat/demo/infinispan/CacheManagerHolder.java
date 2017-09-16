package it.redhat.demo.infinispan;

import org.infinispan.manager.EmbeddedCacheManager;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
public class CacheManagerHolder {

    private static CacheManagerHolder INSTANCE = null;
    private final EmbeddedCacheManager cacheManager;

    private CacheManagerHolder() {
        cacheManager = new CacheManagerProducer().getCacheManager();
    }

    public static synchronized CacheManagerHolder getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CacheManagerHolder();
        }
        return INSTANCE;
    }

    public EmbeddedCacheManager getCacheManager() {
        return cacheManager;
    }

}
