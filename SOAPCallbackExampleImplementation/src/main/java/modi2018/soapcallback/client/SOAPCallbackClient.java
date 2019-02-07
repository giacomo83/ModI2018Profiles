package modi2018.soapcallback.client;

import java.util.UUID;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

import org.apache.cxf.ws.addressing.WSAddressingFeature;

public class SOAPCallbackClient {

    public static void main(String[] args) {

        String address = "http://localhost:8181/soap/nomeinterfacciaservizio/v1";

        WSAddressingFeature addrFeat = new WSAddressingFeature();
        SOAPCallbackClientInterfaceImpl implementor = new SOAPCallbackClientInterfaceImpl();
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(SOAPCallbackClientInterface.class);
        factory.setAddress(address);
        factory.setServiceBean(implementor);
        factory.getFeatures().add(addrFeat);
        factory.getInInterceptors().add(new AttachmentInInterceptor());
        Server create = factory.create();

        //new Thread(new ClientThread()).start();
        //Endpoint endpoint = Endpoint.publish(address, new SOAPCallbackClientInterfaceImpl());
    }
}

class AttachmentInInterceptor extends AbstractPhaseInterceptor<Message> {

    public AttachmentInInterceptor() {
        super(Phase.RECEIVE);
    }

    @Override
    public void handleMessage(Message message) {
        System.out.println("Received");
        int tot = 0;
        for (Interceptor<? extends Message> i: message.getInterceptorChain()) {
            tot++;
        }
        System.out.println("Number of interceptors: " + tot);
    }

    @Override
    public void handleFault(Message messageParam) {
        System.out.println("Faulted");
    }
}

class ClientThread implements Runnable {

    @Override
    public void run() {
        String serverAddress = "http://localhost:8080/soap/nomeinterfacciaservizio/v1";
        String address = "http://localhost:8181/soap/nomeinterfacciaservizio/v1";
        try { // Call Web Service Operation
            WSAddressingFeature addrFeat = new WSAddressingFeature();
            modi2018.soapcallback.client.serverreference.SOAPCallbackService service = new modi2018.soapcallback.client.serverreference.SOAPCallbackService(addrFeat);
            modi2018.soapcallback.client.serverreference.SOAPCallback port = service.getSOAPCallbackPort(addrFeat);
            BindingProvider bindingProvider = (BindingProvider) port;
            bindingProvider.getRequestContext().put(
                    BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                    serverAddress);
            // TODO initialize WS operation arguments here
            modi2018.soapcallback.client.serverreference.MType m = new modi2018.soapcallback.client.serverreference.MType();
            m.setOId(1234);
            modi2018.soapcallback.client.serverreference.AComplexType a = new modi2018.soapcallback.client.serverreference.AComplexType();
            a.getA1S().add("1");
            a.setA2("prova");
            m.setA(a);
            m.setB("prova");
            // TODO process result here
            String guid = UUID.randomUUID().toString();
            System.out.println("Generated message with UUID: " + guid);
            AddressingProperties addressProp = new AddressingProperties();
            AttributedURIType messageId = new AttributedURIType();
            messageId.setValue(guid);
            addressProp.setMessageID(messageId);
            EndpointReferenceType ert = new EndpointReferenceType();
            AttributedURIType replyToAddress = new AttributedURIType();
            replyToAddress.setValue(address);
            ert.setAddress(replyToAddress);
            addressProp.setReplyTo(ert);
            bindingProvider.getRequestContext().put(org.apache.cxf.ws.addressing.JAXWSAConstants.CLIENT_ADDRESSING_PROPERTIES, addressProp);
            port.mRequest(m);
            System.out.println("ACK Received " + bindingProvider.getResponseContext().get(MessageContext.HTTP_RESPONSE_CODE));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
