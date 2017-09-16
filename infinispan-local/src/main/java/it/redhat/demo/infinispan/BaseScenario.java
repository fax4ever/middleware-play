package it.redhat.demo.infinispan;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.infinispan.Cache;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
public abstract class BaseScenario {

    public static final String MAIN_KEY = "mainKey";

    protected final Cache<String, Map<String, Integer>> cache;
    protected final ExecutorService executor = Executors.newSingleThreadExecutor();

    public BaseScenario(Cache<String, Map<String, Integer>> cache) {
        this.cache = cache;
    }

    public void perform() throws RollbackException {

        cache.clear();

        try {
            insertData();
            updateDataOnDifferentThreads();
        } catch (HeuristicMixedException | HeuristicRollbackException | SystemException | NotSupportedException | InterruptedException | ExecutionException e) {
            // exceptions from RollbackException are not foreseen
            throw new RuntimeException(e);
        }

    }

    protected abstract void insertData() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException;

    protected abstract void updateDataOnDifferentThreads() throws SystemException, NotSupportedException, ExecutionException, InterruptedException, HeuristicRollbackException, HeuristicMixedException, RollbackException;

    public Map<String, Map<String, Integer>> getResult() {
        return cache;
    }

}
