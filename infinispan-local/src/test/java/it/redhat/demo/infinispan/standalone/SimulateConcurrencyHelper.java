package it.redhat.demo.infinispan.standalone;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import org.infinispan.Cache;
import org.infinispan.atomic.AtomicMapLookup;
import org.infinispan.atomic.FineGrainedAtomicMap;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
public class SimulateConcurrencyHelper {

    public static Future<?> updateEntryInConcurrentThread(Cache cache, String key, String subKey, int value) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        return executor.submit(() -> {

            TransactionManager tm = cache.getAdvancedCache().getTransactionManager();
            try {
                tm.begin();
            } catch (NotSupportedException | SystemException e) {
                throw new RuntimeException(e);
            }

            FineGrainedAtomicMap<String, Integer> fineGrainedAtomicMap = AtomicMapLookup.getFineGrainedAtomicMap(cache, key);
            fineGrainedAtomicMap.put(subKey, value);

            try {
                tm.commit();
            } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SystemException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public static Future<?> updateMapInConcurrentThread(Cache<String, Map<String, Integer>> cache, String key, String subKey, int value) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        return executorService.submit(() -> {

            TransactionManager tm = cache.getAdvancedCache().getTransactionManager();
            try {
                tm.begin();
            } catch (NotSupportedException | SystemException e) {
                throw new RuntimeException(e);
            }

            Map<String, Integer> entry = cache.get(key);
            entry.put(subKey, value);
            cache.put(key, entry);

            try {
                tm.commit();
            } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SystemException e) {
                throw new RuntimeException(e);
            }

        });

    }

}
