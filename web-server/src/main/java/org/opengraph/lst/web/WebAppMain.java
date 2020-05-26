package org.opengraph.lst.web;

import javax.servlet.Filter;

import org.opengraph.lst.web.configs.AppConfig;
import org.opengraph.lst.web.configs.WebConfig;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppMain extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
    @Override
	protected Filter[] getServletFilters() {
		Filter[] filters = new Filter[1];
		filters[0] = new DelegatingFilterProxy("springSecurityFilterChain");
		return filters;
	}
}
