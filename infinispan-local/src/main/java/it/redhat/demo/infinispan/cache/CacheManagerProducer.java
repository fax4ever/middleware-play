package it.redhat.demo.infinispan.cache;

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

    public EmbeddedCacheManager getCacheManager(IsolationLevel isolationLevel, LockingMode lockingMode, boolean writeSkewCheck) {

        DefaultCacheManager defaultCacheManager = new DefaultCacheManager(getGlobalConfig(), getCongig(isolationLevel, lockingMode, writeSkewCheck));
        return defaultCacheManager;

    }

    private Configuration getCongig(IsolationLevel isolationLevel, LockingMode lockingMode, boolean writeSkewCheck) {
        Configuration defaultConfig = getDefaultConfig();

        return new ConfigurationBuilder()
            .read(defaultConfig)
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
            .locking()
                .concurrencyLevel(10)
                .useLockStriping(false)
            .invocationBatching()
                .enable(true)
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
