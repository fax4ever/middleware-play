package it.redhat.demo.infinispan.standalone;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.TransactionManager;

import org.infinispan.Cache;
import org.infinispan.container.entries.CacheEntry;
import org.infinispan.filter.AcceptAllKeyValueFilter;
import org.infinispan.filter.CacheFilters;
import org.infinispan.filter.Converter;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.marshall.core.ExternalPojo;
import org.infinispan.metadata.Metadata;
import org.infinispan.stream.CacheCollectors;
import org.infinispan.util.function.SerializablePredicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
@RunWith(JUnit4.class)
public class DistCacheTest {

    public static final String INFINISPAN_DIST_XML = "infinispan-dist.xml";

    @Test
    public void test_default_named() throws Exception {
        Cache<String, String> cache = new DefaultCacheManager(INFINISPAN_DIST_XML).getCache("DEFAULT");
        TransactionManager tm = cache.getAdvancedCache().getTransactionManager();

        assertNotNull(cache);
        assertNotNull(tm);

        tm.begin();
        try {
            cache.put("converted-key", "converted-value");

            try (Stream<CacheEntry<String, String>> stream = cache.getAdvancedCache().cacheEntrySet().stream().
                    filter(CacheFilters.predicate(AcceptAllKeyValueFilter.getInstance())).
                    map(CacheFilters.function(new StringTruncator(2, 5)))) {
                Map<String, String> results = mapFromStream(stream);

                String value = results.get("converted-key");
                assertNotNull(value);
                assertEquals("nvert", value);

            }
        } finally {
            tm.rollback();
        }

    }

    @Test
    public void test_default_named_2() throws Exception {
        Cache<String, String> cache = new DefaultCacheManager(INFINISPAN_DIST_XML).getCache("DEFAULT");
        TransactionManager tm = cache.getAdvancedCache().getTransactionManager();

        assertNotNull(cache);
        assertNotNull(tm);

        tm.begin();
        try {
            cache.put("converted-key", "converted-value");

            Map<String, String> results = cache.getAdvancedCache().cacheEntrySet().stream().
                    filter(CacheFilters.predicate(AcceptAllKeyValueFilter.getInstance())).
                    map(CacheFilters.function(new StringTruncator(2, 5)))
                    .collect(CacheCollectors.serializableCollector(() -> Collectors.toMap(e -> e.getKey(), e -> e.getValue())));

            String value = results.get("converted-key");
            assertNotNull(value);
            assertEquals("nvert", value);

        } finally {
            tm.rollback();
        }

    }

    @Test
    public void test_default_named_3() throws Exception {
        Cache<String, Map<String, Object>> cache = new DefaultCacheManager(INFINISPAN_DIST_XML).getCache("DEFAULT");
        TransactionManager tm = cache.getAdvancedCache().getTransactionManager();

        assertNotNull(cache);
        assertNotNull(tm);

        tm.begin();
        try {
            cache.put("converted-key",
                      Collections.singletonMap("key", 7));

            Map<String, Map> results = cache.getAdvancedCache().cacheEntrySet().stream().
                    filter(CacheFilters.predicate(AcceptAllKeyValueFilter.getInstance())).
                    map(CacheFilters.function(new MapConverter()))
                    .collect(CacheCollectors.serializableCollector(() -> Collectors.toMap(e -> e.getKey(), e -> e.getValue())));

            Map value = results.get("converted-key");
            assertNotNull(value);
            assertNotNull(value.get("key"));
            assertEquals(7, value.get("key"));

        } finally {
            tm.rollback();
        }

    }

    @Test
    public void tuples() throws Exception {

        Cache<String, Map<String, Object>> cache = new DefaultCacheManager(INFINISPAN_DIST_XML).getCache("DEFAULT");

        cache.put("converted-key", Collections.singletonMap("key", 7));

        Map<String, Map<String, Object>> results = cache.getAdvancedCache().cacheEntrySet().stream()
                .filter(CacheFilters.predicate(AcceptAllKeyValueFilter.getInstance()))
                .collect(Collectors.toMap(Map.Entry::getKey,
                                          Map.Entry::getValue));

        Map value = results.get("converted-key");
        assertNotNull(value);
        assertNotNull(value.get("key"));
        assertEquals(7, value.get("key"));

    }

    protected static <K, V> Map<K, V> mapFromStream(Stream<CacheEntry<K, V>> stream) {
        return stream.collect(CacheCollectors.serializableCollector(
                () -> Collectors.toMap(e -> e.getKey(), e -> e.getValue())));
    }

    protected static class StringTruncator implements Converter<Object, String, String>,
                                                      Serializable,
                                                      ExternalPojo {
        private final int beginning;
        private final int length;

        public StringTruncator(int beginning, int length) {
            this.beginning = beginning;
            this.length = length;
        }

        @Override
        public String convert(Object key, String value, Metadata metadata) {
            if (value != null && value.length() > beginning + length) {
                return value.substring(beginning, beginning + length);
            } else {
                throw new IllegalStateException("String should be longer than truncation size!  Possible double conversion performed!");
            }
        }
    }

    protected static class MapConverter implements Converter<Object, Map, Map>, Serializable, ExternalPojo {

        @Override
        public Map convert(Object key,
                              Map value,
                              Metadata metadata) {
            return value;
        }
    }

    private static class TupleFilter implements SerializablePredicate<Map.Entry<String, Map<String, Object>>> {

        private static final TupleFilter INSTANCE = new TupleFilter();

        @Override
        public boolean test(Map.Entry<String, Map<String, Object>> cacheEntry) {
            return true;
        }
    }


}
