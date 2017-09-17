package it.redhat.demo.infinispan.standalone;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import javax.transaction.RollbackException;
import javax.transaction.TransactionManager;

import org.infinispan.Cache;
import org.infinispan.atomic.AtomicMapLookup;
import org.infinispan.atomic.FineGrainedAtomicMap;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.stream.CacheCollectors;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
public class FineGrainedAtomicMapTest {

    private static ExecutorService executor;

    @BeforeClass
    public static void setUpExecutor() {
        executor = Executors.newSingleThreadExecutor();
    }

    @Test
    public void test() throws Exception {
        Cache<String, Map<String, String>> cache = new DefaultCacheManager(DistCacheTest.INFINISPAN_DIST_XML).getCache("ENTITIES");

        final String key = "mainKey";

        TransactionManager tm = cache.getAdvancedCache().getTransactionManager();
        tm.begin();

        FineGrainedAtomicMap<String, String> fineGrainedAtomicMap = AtomicMapLookup.getFineGrainedAtomicMap(cache, key);
        fineGrainedAtomicMap.put("k1", "v1");
        fineGrainedAtomicMap.put("k2", "v2");
        fineGrainedAtomicMap.put("k3", "v3");

        tm.commit();
        List<Map.Entry<String, Map<String, String>>> collect = cache.entrySet().stream()
            .collect(CacheCollectors.serializableCollector(() -> Collectors.toList()));

        assertNotNull(collect);

        // 4 item in Infinispan 9.1
        // 1 item in Infinispan 9.0
        assertEquals(4, collect.size());

        tm.begin();

        fineGrainedAtomicMap = AtomicMapLookup.getFineGrainedAtomicMap(cache, key);
        Set<Map.Entry<String, String>> entries = fineGrainedAtomicMap.entrySet();

        assertNotNull(entries);
        assertEquals(3, entries.size());

        tm.commit();

    }

    // no writeSkew Exception on Infinispan 9.0
    @Test(expected = RollbackException.class)
    public void writeSkewTest_onTheSameKey() throws Exception {

        Cache<String, Map<String, Integer>> cache = new DefaultCacheManager(DistCacheTest.INFINISPAN_DIST_XML).getCache("writeSkewTest");

        TransactionManager tm = cache.getAdvancedCache().getTransactionManager();
        tm.begin();

        final String key = "mainKey";

        FineGrainedAtomicMap<String, Integer> fineGrainedAtomicMap = AtomicMapLookup.getFineGrainedAtomicMap(cache, key);
        fineGrainedAtomicMap.put("k1", 1);
        fineGrainedAtomicMap.put("k2", 1);
        fineGrainedAtomicMap.put("k3", 1);

        tm.commit();

        tm.begin();

        fineGrainedAtomicMap = AtomicMapLookup.getFineGrainedAtomicMap(cache, key);

        Future<?> k3 = SimulateConcurrencyHelper.updateEntryInConcurrentThread(cache, key, "k3", 7);
        k3.get();

        fineGrainedAtomicMap.put("k1", 2);
        fineGrainedAtomicMap.put("k2", 2);
        fineGrainedAtomicMap.put("k3", 2);

        tm.commit();

    }

    // no writeSkew Exception on Infinispan 9.0
    @Test(expected = RollbackException.class)
    public void writeSkewTest_onDifferentSubKey() throws Exception {

        Cache<String, Map<String, Integer>> cache = new DefaultCacheManager(DistCacheTest.INFINISPAN_DIST_XML).getCache("writeSkewTest");

        TransactionManager tm = cache.getAdvancedCache().getTransactionManager();
        tm.begin();

        final String key = "mainKey";

        FineGrainedAtomicMap<String, Integer> fineGrainedAtomicMap = AtomicMapLookup.getFineGrainedAtomicMap(cache, key);
        fineGrainedAtomicMap.put("k1", 1);
        fineGrainedAtomicMap.put("k2", 1);
        fineGrainedAtomicMap.put("k3", 1);

        tm.commit();

        tm.begin();

        fineGrainedAtomicMap = AtomicMapLookup.getFineGrainedAtomicMap(cache, key);

        Future<?> k3 = SimulateConcurrencyHelper.updateEntryInConcurrentThread(cache, key, "k3", 7);
        k3.get();

        fineGrainedAtomicMap.put("k1", 2);
        fineGrainedAtomicMap.put("k2", 2);

        tm.commit();

    }

    @Test
    public void noWriteSkewTest_onDifferentKey() throws Exception {

        Cache<String, Map<String, Integer>> cache = new DefaultCacheManager(DistCacheTest.INFINISPAN_DIST_XML).getCache("writeSkewTest");

        TransactionManager tm = cache.getAdvancedCache().getTransactionManager();
        tm.begin();

        final String key = "mainKey";

        FineGrainedAtomicMap<String, Integer> fineGrainedAtomicMap = AtomicMapLookup.getFineGrainedAtomicMap(cache, key);
        fineGrainedAtomicMap.put("k1", 1);
        fineGrainedAtomicMap.put("k2", 1);
        fineGrainedAtomicMap.put("k3", 1);

        tm.commit();

        tm.begin();

        fineGrainedAtomicMap = AtomicMapLookup.getFineGrainedAtomicMap(cache, key);

        Future<?> k3 = SimulateConcurrencyHelper.updateEntryInConcurrentThread(cache, key+"_", "k3", 7);
        k3.get();

        fineGrainedAtomicMap.put("k1", 2);
        fineGrainedAtomicMap.put("k2", 2);
        fineGrainedAtomicMap.put("k3", 2);

        tm.commit();

    }

}
