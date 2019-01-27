package modi2018.restmom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.apache.cxf.jaxrs.openapi.OpenApiFeature;

public class RESTMOMServer {

    public static Connection connection = null;
    public static MessageConsumer consumer = null;
    public static HashMap<String, String> destinationQueues = new HashMap<String, String>();
    public static HashMap<String, String> correlationIdToClient = new HashMap<String, String>();

    public static void main(String args[]) throws InterruptedException {
        System.setProperty("org.apache.cxf.useSpringClassHelpers", "false");
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("MQUEUE");
            consumer = session.createConsumer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
            return;
        }
        
        Swagger2Feature feature = new Swagger2Feature();
        feature.setBasePath("/rest/v1/nomeinterfacciaservizio");
        feature.setScanAllResources(true);
        feature.setPrettyPrint(true);
        feature.setVersion("1.0");
        feature.setTitle("RESTbusywaiting");
        
        OpenApiFeature openapifeature = new OpenApiFeature();
        openapifeature.setScan(true);
        openapifeature.setPrettyPrint(true);
        openapifeature.setVersion("1.0");
        openapifeature.setTitle("RESTbusywaiting");
        
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.getFeatures().add(feature);
        factoryBean.getFeatures().add(openapifeature);
        List<Object> providers = new ArrayList<Object>();
        providers.add(new JacksonJaxbJsonProvider());
        factoryBean.setProviders(providers);
        factoryBean.setResourceClasses(RESTMOMImpl.class);
        factoryBean.setResourceProvider(new SingletonResourceProvider(new RESTMOMImpl()));
        factoryBean.setAddress("http://localhost:8080/rest/v1/nomeinterfacciaservizio");
        factoryBean.create();
        
        JAXRSServerFactoryBean factoryInternalBean = new JAXRSServerFactoryBean();
        List<Object> internalProviders = new ArrayList<Object>();
        internalProviders.add(new JacksonJaxbJsonProvider());
        factoryInternalBean.setProviders(internalProviders);
        factoryInternalBean.setResourceClasses(RESTMOMInternal.class);
        factoryInternalBean.setResourceProvider(new SingletonResourceProvider(new RESTMOMInternal()));
        factoryInternalBean.setAddress("http://localhost:8181/rest/v1/nomeinterfacciaservizio");
        factoryInternalBean.create();
    }
}
