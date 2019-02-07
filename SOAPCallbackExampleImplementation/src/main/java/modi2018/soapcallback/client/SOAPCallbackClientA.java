/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modi2018.soapcallback.client;

import java.net.URL;
import java.util.UUID;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.WSAddressingFeature;

/**
 *
 * @author Francesco
 */
public class SOAPCallbackClientA {
    public static void main(String[] args) {
        String serverAddress = "http://localhost:8080/soap/nomeinterfacciaservizio/v1";
        String address = "http://localhost:8181/soap/nomeinterfacciaservizio/v1";
        try { // Call Web Service Operation
            modi2018.soapcallback.client.serverreference.SOAPCallbackService service = new modi2018.soapcallback.client.serverreference.SOAPCallbackService(new URL("http://localhost:8080/soap/nomeinterfacciaservizio/v1?wsdl"));
            modi2018.soapcallback.client.serverreference.SOAPCallback port = service.getSOAPCallbackPort(new WSAddressingFeature());
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
            System.out.println("ACK Received");
            System.out.println("ACK Received " + bindingProvider.getResponseContext().get(MessageContext.HTTP_RESPONSE_CODE));
            port.mRequest(m);
            System.out.println("ACK Received");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
