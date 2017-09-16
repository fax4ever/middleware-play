package it.redhat.demo.infinispan;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.TransactionManager;

import org.infinispan.Cache;
import org.infinispan.atomic.AtomicMapLookup;
import org.infinispan.atomic.FineGrainedAtomicMap;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.stream.CacheCollectors;
import org.junit.Test;

import static it.redhat.demo.infinispan.DistCacheTest.INFINISPAN_DIST_XML;
import static org.junit.Assert.*;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
public class FineGrainedAtomicMapTest {

    @Test
    public void test() throws Exception {
        Cache<String, Map<String, String>> cache = new DefaultCacheManager(INFINISPAN_DIST_XML).getCache("ENTITY");

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
        assertEquals(4, collect.size());

        tm.begin();

        fineGrainedAtomicMap = AtomicMapLookup.getFineGrainedAtomicMap(cache, key);
        Set<Map.Entry<String, String>> entries = fineGrainedAtomicMap.entrySet();

        assertNotNull(entries);
        assertEquals(3, entries.size());

        tm.commit();

    }

}
