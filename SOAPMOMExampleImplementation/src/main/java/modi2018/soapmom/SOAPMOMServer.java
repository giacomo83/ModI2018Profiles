package modi2018.soapmom;

import java.util.HashMap;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.ws.addressing.WSAddressingFeature;

public class SOAPMOMServer {

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

        SOAPMOMImpl implementor = new SOAPMOMImpl();
        String address = "http://localhost:8080/soap/nomeinterfacciaservizio/v1";
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(SOAPMOMImpl.class);
        factory.setAddress(address);
        factory.setServiceBean(implementor);
        factory.getFeatures().add(new WSAddressingFeature());
        factory.create();

        SOAPMOMInternal internal = new SOAPMOMInternal();
        String internalAddress = "http://localhost:8181/soap/nomeinterfacciaservizio/v1";
        JaxWsServerFactoryBean internalFactory = new JaxWsServerFactoryBean();
        internalFactory.setServiceClass(SOAPMOMInternal.class);
        internalFactory.setAddress(internalAddress);
        internalFactory.setServiceBean(internal);
        internalFactory.getFeatures().add(new WSAddressingFeature());
        internalFactory.create();
    }
}
