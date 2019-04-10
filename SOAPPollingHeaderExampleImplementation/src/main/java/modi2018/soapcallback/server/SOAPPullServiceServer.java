package modi2018.soapcallback.server;

import org.apache.cxf.binding.BindingConfiguration;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import modi2018.soapcallback.client.serverreference.SOAPPull;


public class SOAPPullServiceServer {

    public static void main(String args[]) throws InterruptedException {
       
    	SOAPPullServiceImpl implementor = new SOAPPullServiceImpl();
        String address = "http://localhost:8080/soap/nomeinterfacciaservizio/v1";
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        
        BindingConfiguration config = new BindingConfiguration() {

	            @Override
	            public String getBindingId() {
	                    
	                    return javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING; 
	            }
            };
        factory.setBindingConfig(config);
        
        
        factory.setServiceClass(SOAPPullServiceImpl.class);
        factory.setAddress(address);
        factory.setServiceBean(implementor);
        
        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getOutInterceptors().add(new LoggingOutInterceptor());
        
        Server create = factory.create();
        System.out.println("Created Server for service:"+create.getEndpoint().getService().getName());
        
        
    }
}
