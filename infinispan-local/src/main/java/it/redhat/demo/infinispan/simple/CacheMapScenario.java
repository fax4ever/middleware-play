package it.redhat.demo.infinispan.simple;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import it.redhat.demo.infinispan.BaseScenario;
import org.infinispan.Cache;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
public class CacheMapScenario extends BaseScenario {

    public CacheMapScenario(Cache<String, Map<String, Integer>> cache) {
        super(cache);
    }

    @Override
    protected void insertData() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException, NotSupportedException {
        TransactionManager tm = cache.getAdvancedCache().getTransactionManager();
        tm.begin();

        Map<String, Integer> entry = new HashMap<>();
        entry.put("k1", 1);
        entry.put("k2", 1);
        entry.put("k3", 1);

        cache.put(MAIN_KEY, entry);

        tm.commit();

    }

    @Override
    protected void updateDataOnDifferentThreads() throws SystemException, NotSupportedException, ExecutionException, InterruptedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        TransactionManager tm = cache.getAdvancedCache().getTransactionManager();
        tm.begin();

        Map<String, Integer> entry = cache.get(MAIN_KEY);

        Future<?> task = executor.submit(new CacheMapConcurrentUpdate(cache, MAIN_KEY, "k3", 7));
        task.get();

        entry.put("k1", 2);
        entry.put("k2", 2);
        entry.put("k3", 2);

        tm.commit();

    }

}
