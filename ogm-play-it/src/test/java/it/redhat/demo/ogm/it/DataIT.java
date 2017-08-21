/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

/**
 * @author Fabio Massimo Ercoli (C) 2017 Red Hat Inc.
 */
package it.redhat.demo.ogm.it;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class DataIT {

    private final static Logger LOG = LoggerFactory.getLogger(DataIT.class);

    @Deployment
    public static WebArchive create() {

        PomEquippedResolveStage resolveStage = Maven.resolver().loadPomFromFile("./pom.xml");
        File file = resolveStage
                .resolve("it.redhat.demo:uisp-2017:war:1.0.0-SNAPSHOT")
                .withoutTransitivity().asSingleFile();
        return ShrinkWrap.createFromZipFile(WebArchive.class, file);

    }

    @ArquillianResource
    private URL deploymentURL;

    @Test
    @RunAsClient
    public void test() {

        LOG.info("server url {}", deploymentURL);

        ResteasyClient client = new ResteasyClientBuilder()
            .connectionPoolSize(1)
            .establishConnectionTimeout(100, TimeUnit.SECONDS)
            .socketTimeout(2, TimeUnit.SECONDS)
            .build();

        // insert 100 athletes
        Response response = client
            .target(deploymentURL.toString())
            .path("athletes")
            .path("bulk")
            .path("100")
            .request().post(Entity.json(""));

        int status = response.getStatus();
        LOG.info("insert 100 athletes -> {}", status);

        assertTrue(status >= 200);
        assertTrue(status <= 300);

        response.close();

        // insert 10 clubs
        response = client
            .target(deploymentURL.toString())
            .path("clubs")
            .path("bulk")
            .path("100")
            .request().post(Entity.json(""));

        LOG.info("insert 10 clubs -> {}", status);

        assertTrue(status >= 200);
        assertTrue(status <= 300);

        response.close();



    }

}
