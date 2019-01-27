/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agid.restrobustezza;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.jaxrs.openapi.OpenApiFeature;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;

/**
 *
 * @author Francesco
 */
public class RESTRobustezzaServer {
    public static void main(String[] args) {
        //Per ottenere lo swagger andare http://localhost:8080/rest/v1/nomeinterfacciaservizio/swagger.json o http://localhost:8080/rest/v1/nomeinterfacciaservizio/swagger.yaml
        Swagger2Feature feature = new Swagger2Feature();
        feature.setBasePath("/restblocking");
        feature.setScanAllResources(true);
        feature.setPrettyPrint(true);
        feature.setVersion("1.0");
        feature.setTitle("RESTrobustezza");
        
        OpenApiFeature openapifeature = new OpenApiFeature();
        openapifeature.setScan(true);
        openapifeature.setPrettyPrint(true);
        openapifeature.setVersion("1.0");
        openapifeature.setTitle("RESTrobustezza");

        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.getFeatures().add(feature);
        factoryBean.getFeatures().add(openapifeature);
        List<Object> providers = new ArrayList<Object>();
        providers.add(new JacksonJaxbJsonProvider());
        factoryBean.setProviders(providers);
        factoryBean.setResourceClasses(RESTBlockingImpl.class);
        factoryBean.setResourceProvider(new SingletonResourceProvider(new RESTBlockingImpl()));
        factoryBean.setAddress("http://localhost:8080/rest/v1/nomeinterfacciaservizio");
        factoryBean.create();
    }
}
