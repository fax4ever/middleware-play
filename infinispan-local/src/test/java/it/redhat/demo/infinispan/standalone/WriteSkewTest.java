package it.redhat.demo.infinispan.standalone;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import javax.transaction.TransactionManager;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.Test;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
public class WriteSkewTest {

    @Test
    public void no_writeSkewTest_onTheSameKey() throws Exception {

        Cache<String, Map<String, Integer>> cache = new DefaultCacheManager(DistCacheTest.INFINISPAN_DIST_XML).getCache("writeSkewTestCache");

        TransactionManager tm = cache.getAdvancedCache().getTransactionManager();
        tm.begin();

        final String key = "mainKey";

        Map<String, Integer> entry = new HashMap<>();
        entry.put("k1", 1);
        entry.put("k2", 1);
        entry.put("k3", 1);

        cache.put(key, entry);

        tm.commit();

        tm.begin();

        entry = cache.get(key);

        Future<?> k3 = SimulateConcurrencyHelper.updateMapInConcurrentThread(cache, key, "k3", 7);
        k3.get();

        entry.put("k1", 2);
        entry.put("k2", 2);
        entry.put("k3", 2);

        tm.commit();

    }

}
