package modi2018.soapcallback.client;

import javax.xml.ws.Holder;

import org.apache.cxf.binding.BindingConfiguration;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import modi2018.soapcallback.client.serverreference.MRequestResponse;
import modi2018.soapcallback.client.serverreference.SOAPCallback;

public class SOAPCallbackClient {

    public static void main(String[] args) {

        String address = "http://localhost:8181/soap/nomeinterfacciaservizio/v1";

        SOAPCallbackClientImpl implementor = new SOAPCallbackClientImpl();
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        BindingConfiguration config = new BindingConfiguration() {

            @Override
            public String getBindingId() {
                    
                    return javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING; 
            }
        };
        factory.setBindingConfig(config);
        
        factory.setServiceClass(SOAPCallbackClientImpl.class);
        factory.setAddress(address);
        factory.setServiceBean(implementor);
        
        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getOutInterceptors().add(new LoggingOutInterceptor());
        
        
        Server create = factory.create();
        System.out.println("Created Server for service:"+create.getEndpoint().getService().getName());

        new Thread(new ClientThread()).start();
    }
}

class ClientThread implements Runnable {

    @Override
    public void run() {
        String serverAddress = "http://localhost:8080/soap/nomeinterfacciaservizio/v1";
        String address = "http://localhost:8181/soap/nomeinterfacciaservizio/v1";
        try { // Call Web Service Operation
            
        	JaxWsProxyFactoryBean  factory = new JaxWsProxyFactoryBean();
            factory.setServiceClass(SOAPCallback.class);
            factory.setAddress(serverAddress);
            BindingConfiguration config = new BindingConfiguration() {

	            @Override
	            public String getBindingId() {
	                    
	                    return javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING; 
	            }
            };
            factory.setBindingConfig(config);
            
            SOAPCallback port = (SOAPCallback)factory.create();
           
        	     
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
            //port.mRequest(mReq, address, resp, correlationId);
            //System.out.println("Correlation ID: " + correlationId.value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
