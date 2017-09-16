package it.redhat.demo.infinispan.atomic;

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
public class AtomicMapConcurrentUpdate implements Runnable {

    private final Cache cache;
    private final String key;
    private final String subKey;
    private final int value;

    public AtomicMapConcurrentUpdate(Cache cache, String key, String subKey, int value) {
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
            FineGrainedAtomicMap<String, Integer> fineGrainedAtomicMap = AtomicMapLookup.getFineGrainedAtomicMap(cache, key);
            fineGrainedAtomicMap.put(subKey, value);
            tm.commit();

        } catch (NotSupportedException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SystemException e) {
            throw new RuntimeException(e);
        }

    }

}
