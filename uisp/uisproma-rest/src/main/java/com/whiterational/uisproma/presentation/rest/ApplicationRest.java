package com.whiterational.uisproma.presentation.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

@ApplicationPath("resources")
public class ApplicationRest extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> set = new HashSet<Class<?>>();
		
		set.add(AthleteRestService.class);
		set.add(ExternalRestService.class);
		
		return set;
	}

	@Override
	public Set<Object> getSingletons() {
		ObjectMapper mapper = new ObjectMapper();
	    JaxbAnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
	    // make deserializer use JAXB annotations (only)
	    mapper.getDeserializationConfig().withAnnotationIntrospector(introspector);
	    // make serializer use JAXB annotations (only)
	    mapper.getSerializationConfig().withAnnotationIntrospector(introspector);
	    
	    mapper.setAnnotationIntrospector(introspector);
		
		JacksonJsonProvider jackson = new JacksonJsonProvider(mapper);
		
		HashSet<Object> set = new HashSet<Object>(1);
        set.add(jackson);
        return set;
	}
	
}
