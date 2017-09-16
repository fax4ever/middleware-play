package it.redhat.demo.infinispan;

import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.transaction.LockingMode;
import org.infinispan.transaction.TransactionMode;
import org.infinispan.transaction.lookup.GenericTransactionManagerLookup;
import org.infinispan.util.concurrent.IsolationLevel;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
public class CacheManagerProducer {

    public static final String RC_PESS_NOWSC = "RC_PESS_NOWSC";
    public static final String RC_PESS_WSC = "RC_PESS_WSC";
    public static final String RR_PESS_NOWSC = "RR_PESS_NOWSC";
    public static final String RR_PESS_WSC = "RR_PESS_WSC";
    public static final String RC_OPTI_NOWSC = "RC_OPTI_NOWSC";
    public static final String RC_OPTI_WSC = "RC_OPTI_WSC";
    public static final String RR_OPTI_NOWSC = "RR_OPTI_NOWSC";
    public static final String RR_OPTI_WSC = "RR_OPTI_WSC";

    EmbeddedCacheManager getCacheManager() {

        DefaultCacheManager cacheManager = new DefaultCacheManager(getGlobalConfig(), getDefaultConfig());
        cacheManager.defineConfiguration(RC_PESS_NOWSC, getConfig(IsolationLevel.READ_COMMITTED, LockingMode.PESSIMISTIC, false));
        cacheManager.defineConfiguration(RC_PESS_WSC, getConfig(IsolationLevel.READ_COMMITTED, LockingMode.PESSIMISTIC, true));
        cacheManager.defineConfiguration(RR_PESS_NOWSC, getConfig(IsolationLevel.REPEATABLE_READ, LockingMode.PESSIMISTIC, false));
        cacheManager.defineConfiguration(RR_PESS_WSC, getConfig(IsolationLevel.REPEATABLE_READ, LockingMode.PESSIMISTIC, true));
        cacheManager.defineConfiguration(RC_OPTI_NOWSC, getConfig(IsolationLevel.READ_COMMITTED, LockingMode.OPTIMISTIC, false));
        cacheManager.defineConfiguration(RC_OPTI_WSC, getConfig(IsolationLevel.READ_COMMITTED, LockingMode.OPTIMISTIC, true));
        cacheManager.defineConfiguration(RR_OPTI_NOWSC, getConfig(IsolationLevel.REPEATABLE_READ, LockingMode.OPTIMISTIC, false));
        cacheManager.defineConfiguration(RR_OPTI_WSC, getConfig(IsolationLevel.REPEATABLE_READ, LockingMode.OPTIMISTIC, true));

        return cacheManager;

    }

    private Configuration getConfig(IsolationLevel isolationLevel, LockingMode lockingMode, boolean writeSkewCheck) {

        return new ConfigurationBuilder()
            .read(getDefaultConfig())
            .transaction()
                .lockingMode(lockingMode)
            .locking()
                .writeSkewCheck(writeSkewCheck)
                .isolationLevel(isolationLevel)
            .build();

    }

    private Configuration getDefaultConfig() {
        return new ConfigurationBuilder()
            .transaction()
                .transactionMode(TransactionMode.TRANSACTIONAL)
                .transactionManagerLookup(new GenericTransactionManagerLookup())
                .useSynchronization(false)
            .clustering().hash().groups().enabled()
            .locking()
                .concurrencyLevel(10)
                .useLockStriping(false)
            .build();
    }

    private GlobalConfiguration getGlobalConfig() {

        return new GlobalConfigurationBuilder()
            .transport()
                .defaultTransport()
                .clusterName("infinispan-91-cluster")
                .distributedSyncTimeout(600000l)
                .addProperty("configurationFile", "testing-flush-loopback.xml")
            .globalJmxStatistics()
                .allowDuplicateDomains(true)
                .enable()
            .build();

    }

}
