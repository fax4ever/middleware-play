package com.whiterational.uisproma.presentation.faces;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceBundleProducer {

	@Inject
	public Locale locale;

	@Inject
	public FacesContext facesContext;

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceBundleProducer.class);

	@Produces
	public ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle("com.whiterational.uisproma.application", facesContext.getViewRoot().getLocale());
	}

	@Produces
	public Properties getProperties() {
		Properties prop = new Properties();

		try {
			prop.load(this.getClass().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			LOGGER.error(e + "", e);
		}

		return prop;
	}

}
