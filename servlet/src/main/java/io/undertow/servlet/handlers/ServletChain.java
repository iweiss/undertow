/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.undertow.servlet.handlers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import io.undertow.server.HttpHandler;
import io.undertow.servlet.core.ManagedFilter;
import io.undertow.servlet.core.ManagedServlet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;

/**
* @author Stuart Douglas
*/
public class ServletChain {
    private final HttpHandler handler;
    private final ManagedServlet managedServlet;
    private final String servletPath;
    private final Executor executor;
    private final boolean defaultServletMapping;
    private final Map<DispatcherType, List<ManagedFilter>> filters;

    public ServletChain(final HttpHandler handler, final ManagedServlet managedServlet, final String servletPath, boolean defaultServletMapping, Map<DispatcherType, List<ManagedFilter>> filters) {
        this.handler = handler;
        this.managedServlet = managedServlet;
        this.servletPath = servletPath;
        this.defaultServletMapping = defaultServletMapping;
        this.executor = managedServlet.getServletInfo().getExecutor();
        this.filters = filters;
    }

    public ServletChain(final ServletChain other) {
        this(other.getHandler(), other.getManagedServlet(), other.getServletPath(), other.isDefaultServletMapping(), other.filters);
    }

    public HttpHandler getHandler() {
        return handler;
    }

    public ManagedServlet getManagedServlet() {
        return managedServlet;
    }

    /**
     *
     * @return The servlet path part
     */
    public String getServletPath() {
        return servletPath;
    }

    public Executor getExecutor() {
        return executor;
    }

    public boolean isDefaultServletMapping() {
        return defaultServletMapping;
    }

    //see UNDERTOW-1132
    public void forceInit(DispatcherType dispatcherType) throws ServletException {
        managedServlet.forceInit();
        if(filters != null) {
            List<ManagedFilter> list = filters.get(dispatcherType);
            if(list != null && !list.isEmpty()) {
                for(int i = 0; i < list.size(); ++i) {
                    ManagedFilter filter = list.get(i);
                    filter.forceInit();
                }
            }
        }

    }
}
