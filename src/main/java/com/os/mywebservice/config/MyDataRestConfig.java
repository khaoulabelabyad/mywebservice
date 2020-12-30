package com.os.mywebservice.config;

import com.os.mywebservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//This class is used to restrict the exposure of some HttpMethods to the user
@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

//        HttpMethod[] supportedActions = {HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE};
//        enableHttpMethods(Customer.class, config, supportedActions);
        // call an internal helper method to expose the ids
        exposeIds(config);
    }

    private void enableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] supportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.enable(supportedActions)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.enable(supportedActions)));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        // this is used to expose entity ids

        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        List<Class> entityClasses = new ArrayList<>();

        for (EntityType entityType : entities) {
            entityClasses.add(entityType.getJavaType());
        }

        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
