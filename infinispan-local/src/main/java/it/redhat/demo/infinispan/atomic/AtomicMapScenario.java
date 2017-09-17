package it.redhat.demo.infinispan.atomic;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import it.redhat.demo.infinispan.BaseScenario;
import org.infinispan.Cache;
import org.infinispan.atomic.AtomicMapLookup;
import org.infinispan.atomic.FineGrainedAtomicMap;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
public class AtomicMapScenario extends BaseScenario {

    public AtomicMapScenario(Cache<String, Map<String, Integer>> cache) {
        super(cache);
    }

    @Override
    protected void insertData() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        TransactionManager tm = cache.getAdvancedCache().getTransactionManager();
        tm.begin();

        try {
            FineGrainedAtomicMap<String, Integer> fineGrainedAtomicMap = AtomicMapLookup.getFineGrainedAtomicMap(cache, MAIN_KEY);
            fineGrainedAtomicMap.put("k1", 1);
            fineGrainedAtomicMap.put("k2", 1);
            fineGrainedAtomicMap.put("k3", 1);
        } finally {
            tm.commit();
        }

    }

    @Override
    protected void updateDataOnDifferentThreads() throws SystemException, NotSupportedException, ExecutionException, InterruptedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        TransactionManager tm = cache.getAdvancedCache().getTransactionManager();
        tm.begin();

        try {
            FineGrainedAtomicMap<Object, Object> fineGrainedAtomicMap = AtomicMapLookup.getFineGrainedAtomicMap(cache, MAIN_KEY);

            Future<?> task = executor.submit(new AtomicMapConcurrentUpdate(cache, MAIN_KEY, "k3", 7));
            task.get();

            fineGrainedAtomicMap.put("k1", 2);
            fineGrainedAtomicMap.put("k2", 2);
            fineGrainedAtomicMap.put("k3", 2);
        } finally {
            tm.commit();
        }


    }

    @Override
    public Map<String, Map<String, Integer>> getResult() {
        FineGrainedAtomicMap<String, Integer> fineGrainedAtomicMap = AtomicMapLookup.getFineGrainedAtomicMap(cache, MAIN_KEY);

        Map<String, Integer> externalValue = fineGrainedAtomicMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return Collections.singletonMap(MAIN_KEY, externalValue);

    }

}
