package it.redhat.demo.infinispan.simple;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.transaction.RollbackException;

import it.redhat.demo.infinispan.CacheManagerHolder;
import it.redhat.demo.infinispan.CacheManagerProducer;
import it.redhat.demo.infinispan.atomic.AtomicMapScenario;
import org.infinispan.manager.EmbeddedCacheManager;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
public class SimpleMapScenarioTest {

    public static EmbeddedCacheManager cacheManager;

    @BeforeClass
    public static void beforeClass() {

        cacheManager = CacheManagerHolder.getInstance().getCacheManager();

    }

    @Test
    public void test_RC_PESS_NOWSC() throws RollbackException {

        CacheMapScenario scenario = new CacheMapScenario(cacheManager.getCache(CacheManagerProducer.RC_PESS_NOWSC));
        scenario.perform();

        Map<String, Map<String, Integer>> result = scenario.getResult();
        assertNotNull(result);
        assertEquals(getExpectedMap(), result);

    }

    @Test
    public void test_RC_PESS_WSC() throws RollbackException {

        CacheMapScenario scenario = new CacheMapScenario(cacheManager.getCache(CacheManagerProducer.RC_PESS_WSC));
        scenario.perform();

        Map<String, Map<String, Integer>> result = scenario.getResult();
        assertNotNull(result);
        assertEquals(getExpectedMap(), result);

    }

    @Test
    public void test_RR_PESS_NOWSC() throws RollbackException {

        CacheMapScenario scenario = new CacheMapScenario(cacheManager.getCache(CacheManagerProducer.RR_PESS_NOWSC));
        scenario.perform();

        Map<String, Map<String, Integer>> result = scenario.getResult();
        assertNotNull(result);
        assertEquals(getExpectedMap(), result);

    }

    @Test
    public void test_RR_PESS_WSC() throws RollbackException {

        CacheMapScenario scenario = new CacheMapScenario(cacheManager.getCache(CacheManagerProducer.RR_PESS_WSC));
        scenario.perform();

        Map<String, Map<String, Integer>> result = scenario.getResult();
        assertNotNull(result);
        assertEquals(getExpectedMap(), result);

    }

    @Test
    public void test_RC_OPTI_NOWSC() throws RollbackException {

        CacheMapScenario scenario = new CacheMapScenario(cacheManager.getCache(CacheManagerProducer.RC_OPTI_NOWSC));
        scenario.perform();

        Map<String, Map<String, Integer>> result = scenario.getResult();
        assertNotNull(result);
        assertEquals(getExpectedMap(), result);

    }

    @Test
    public void test_RC_OPTI_WSC() throws RollbackException {

        CacheMapScenario scenario = new CacheMapScenario(cacheManager.getCache(CacheManagerProducer.RC_OPTI_WSC));
        scenario.perform();

        Map<String, Map<String, Integer>> result = scenario.getResult();
        assertNotNull(result);
        assertEquals(getExpectedMap(), result);

    }

    @Test
    public void test_RR_OPTI_NOWSC() throws RollbackException {

        CacheMapScenario scenario = new CacheMapScenario(cacheManager.getCache(CacheManagerProducer.RR_OPTI_NOWSC));
        scenario.perform();

        Map<String, Map<String, Integer>> result = scenario.getResult();
        assertNotNull(result);
        assertEquals(getExpectedMap(), result);

    }

    @Test
    public void test_RR_OPTI_WSC() throws RollbackException {

        CacheMapScenario scenario = new CacheMapScenario(cacheManager.getCache(CacheManagerProducer.RR_OPTI_WSC));
        scenario.perform();

        Map<String, Map<String, Integer>> result = scenario.getResult();
        assertNotNull(result);
        assertEquals(getExpectedMap(), result);

    }

    public Map<String, HashMap<String, Integer>> getExpectedMap() {

        HashMap<String, Integer> innerMap = new HashMap<>();
        innerMap.put("k1", 2);
        innerMap.put("k2", 2);
        innerMap.put("k3", 2);

        return Collections.singletonMap(AtomicMapScenario.MAIN_KEY, innerMap);

    }

}
