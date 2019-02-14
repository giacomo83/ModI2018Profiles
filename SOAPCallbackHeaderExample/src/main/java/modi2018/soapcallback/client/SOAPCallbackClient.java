package modi2018.soapcallback.client;

import java.net.URL;
import java.util.UUID;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import javax.xml.ws.handler.MessageContext;
import modi2018.soapcallback.client.serverreference.MRequestResponse;
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

        SOAPCallbackClientInterfaceImpl implementor = new SOAPCallbackClientInterfaceImpl();
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(SOAPCallbackClientInterface.class);
        factory.setAddress(address);
        factory.setServiceBean(implementor);
        Server create = factory.create();

        new Thread(new ClientThread()).start();
    }
}

class ClientThread implements Runnable {

    @Override
    public void run() {
        String serverAddress = "http://localhost:8080/soap/nomeinterfacciaservizio/v1";
        String address = "http://localhost:8181/soap/nomeinterfacciaservizio/v1";
        try { // Call Web Service Operation
            modi2018.soapcallback.client.serverreference.SOAPCallbackService service = new modi2018.soapcallback.client.serverreference.SOAPCallbackService(new URL("http://localhost:8080/soap/nomeinterfacciaservizio/v1?wsdl"));
            modi2018.soapcallback.client.serverreference.SOAPCallback port = service.getSOAPCallbackPort();
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
            modi2018.soapcallback.client.serverreference.MRequest mReq = new modi2018.soapcallback.client.serverreference.MRequest();
            mReq.setM(m);
            // TODO process result here
            Holder<MRequestResponse> resp = new Holder<>();
            Holder<String> correlationId = new Holder<>();
            port.mRequest(mReq, address, resp, correlationId);
            System.out.println("Correlation ID: " + correlationId.value);
            port.mRequest(mReq, address, resp, correlationId);
            System.out.println("Correlation ID: " + correlationId.value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
