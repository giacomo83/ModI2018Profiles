package modi2018.restcallback.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.apache.cxf.jaxrs.openapi.OpenApiFeature;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;

public class RESTCallbackServer {
    public static void main(String args[]) throws InterruptedException {
        Swagger2Feature feature = new Swagger2Feature();
        feature.setBasePath("/rest/v1/nomeinterfacciaservizio");
        feature.setScanAllResources(true);
        feature.setPrettyPrint(true);
        feature.setVersion("1.0");
        feature.setTitle("RESTcallback");

        OpenApiFeature openapifeature = new OpenApiFeature();
        openapifeature.setScan(true);
        openapifeature.setPrettyPrint(true);
        openapifeature.setVersion("1.0");
        openapifeature.setTitle("RESTcallback");
        
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.getFeatures().add(feature);
        factoryBean.getFeatures().add(openapifeature);
        List<Object> providers = new ArrayList<>();
        providers.add(new JacksonJaxbJsonProvider());
        factoryBean.setProviders(providers);
        factoryBean.setResourceClasses(RESTCallbackImpl.class);
        factoryBean.setResourceProvider(new SingletonResourceProvider(new RESTCallbackImpl()));
        factoryBean.setAddress("http://localhost:8080/rest/v1/nomeinterfacciaservizio");
        factoryBean.create();
    }
}
