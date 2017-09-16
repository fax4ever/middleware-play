package it.redhat.demo.infinispan.standalone;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import it.redhat.demo.infinispan.atomic.AtomicMapConcurrentUpdate;
import it.redhat.demo.infinispan.simple.CacheMapConcurrentUpdate;
import org.infinispan.Cache;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
public class SimulateConcurrencyHelper {

    public static Future<?> updateEntryInConcurrentThread(Cache cache, String key, String subKey, int value) {

        return Executors.newSingleThreadExecutor().submit(new AtomicMapConcurrentUpdate(cache, key, subKey, value));

    }

    public static Future<?> updateMapInConcurrentThread(Cache<String, Map<String, Integer>> cache, String key, String subKey, int value) {

        return Executors.newSingleThreadExecutor().submit(new CacheMapConcurrentUpdate(cache, key, subKey, value));

    }

}
