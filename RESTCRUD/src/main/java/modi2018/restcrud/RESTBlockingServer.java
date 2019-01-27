package modi2018.restcrud;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.apache.cxf.jaxrs.openapi.OpenApiFeature;

public class RESTBlockingServer {
    public static void main(String args[]) throws InterruptedException {
        //Per ottenere lo swagger andare http://localhost:8080/rest/v1/nomeinterfacciaservizio/swagger.json o http://localhost:8080/rest/v1/nomeinterfacciaservizio/swagger.yaml
        Swagger2Feature feature = new Swagger2Feature();
        feature.setBasePath("/restcrud");
        feature.setScanAllResources(true);
        feature.setPrettyPrint(true);
        feature.setVersion("1.0");
        feature.setTitle("RESTCRUD");
        
        OpenApiFeature openapifeature = new OpenApiFeature();
        openapifeature.setScan(true);
        openapifeature.setPrettyPrint(true);
        openapifeature.setVersion("1.0");
        openapifeature.setTitle("RESTCRUD");

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
