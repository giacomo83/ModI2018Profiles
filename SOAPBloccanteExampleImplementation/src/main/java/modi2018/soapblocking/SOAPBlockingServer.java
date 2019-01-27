package modi2018.soapblocking;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class SOAPBlockingServer {
	public static void main(String args[]) throws InterruptedException {
        SOAPBlockingImpl implementor = new SOAPBlockingImpl();
        String address = "http://localhost:8080/soap/nomeinterfacciaservizio/v1";
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
		factory.setServiceClass(SOAPBlockingImpl.class);
		factory.setAddress(address);
		factory.setServiceBean(implementor);
		factory.create();
    }
}
