package it.redhat.demo.infinispan;

import org.infinispan.Cache;
import org.infinispan.commons.CacheConfigurationException;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertNotNull;

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
@RunWith(JUnit4.class)
public class PlayCacheTest {

    @Test
    public void test_default() throws Exception {
        Cache<Object, Object> c = new DefaultCacheManager("infinispan-local.xml").getCache();

        assertNotNull(c);
    }

    @Test
    public void test_entities() throws Exception {
        Cache<Object, Object> c = new DefaultCacheManager("infinispan-local.xml").getCache("ENTITIES");

        assertNotNull(c);
    }

    @Test(expected = CacheConfigurationException.class)
    // org.infinispan.commons.CacheConfigurationException: ISPN000433: A default cache has been requested, but no cache has been set as default for this container
    public void test_error() throws Exception {
        Cache<Object, Object> c = new DefaultCacheManager().getCache();

        assertNotNull(c);
    }

}
