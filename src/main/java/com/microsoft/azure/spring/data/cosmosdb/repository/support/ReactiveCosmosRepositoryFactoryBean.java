/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure.spring.data.cosmosdb.repository.support;

import com.microsoft.azure.spring.data.cosmosdb.core.ReactiveCosmosOperations;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

public class ReactiveCosmosRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable>
        extends RepositoryFactoryBeanSupport<T, S, ID>
        implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private ReactiveCosmosOperations operations;
    //    private boolean mappingContextConfigured = false;

    public ReactiveCosmosRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Autowired
    public void setDocumentDbOperations(ReactiveCosmosOperations operations) {
        this.operations = operations;
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        return getFactoryInstance(applicationContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected RepositoryFactorySupport getFactoryInstance(ApplicationContext applicationContext) {
        return new ReactiveCosmosRepositoryFactory(operations, applicationContext);
    }

    @Override
    protected void setMappingContext(MappingContext<?, ?> mappingContext) {
        super.setMappingContext(mappingContext);
        //        this.mappingContextConfigured = true;
    }

}
