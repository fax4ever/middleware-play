package it.redhat.demo.infinispan.simple;

import java.util.Map;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import org.infinispan.Cache;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
public class CacheMapConcurrentUpdate implements Runnable {

    private final Cache<String, Map<String, Integer>> cache;
    private final String key;
    private final String subKey;
    private final int value;

    public CacheMapConcurrentUpdate(Cache cache, String key, String subKey, int value) {
        this.cache = cache;
        this.key = key;
        this.subKey = subKey;
        this.value = value;
    }

    @Override
    public void run() {

        TransactionManager tm = cache.getAdvancedCache().getTransactionManager();
        try {

            tm.begin();

            try {
                Map<String, Integer> entry = cache.get(key);
                entry.put(subKey, value);
                cache.put(key, entry);
            } finally {
                tm.commit();
            }


        } catch (NotSupportedException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SystemException e) {
            throw new RuntimeException(e);
        }

    }

}
