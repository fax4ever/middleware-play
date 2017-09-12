package it.redhat.demo.infinispan;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
public class PlayCache {

    public static void main(String args[]) throws Exception {
        Cache<Object, Object> c = new DefaultCacheManager("infinispan-local.xml").getCache();
    }

}
