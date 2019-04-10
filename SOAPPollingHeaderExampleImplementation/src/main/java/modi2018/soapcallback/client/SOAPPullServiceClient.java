package modi2018.soapcallback.client;

import javax.xml.ws.Holder;

import org.apache.cxf.binding.BindingConfiguration;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import modi2018.soapcallback.client.serverreference.AComplexType;
import modi2018.soapcallback.client.serverreference.ErrorMessageException;
import modi2018.soapcallback.client.serverreference.MProcessingStatus;
import modi2018.soapcallback.client.serverreference.MProcessingStatusResponse;
import modi2018.soapcallback.client.serverreference.MRequest;
import modi2018.soapcallback.client.serverreference.MRequestResponse;
import modi2018.soapcallback.client.serverreference.MResponse;
import modi2018.soapcallback.client.serverreference.MResponseResponse;
import modi2018.soapcallback.client.serverreference.MType;
import modi2018.soapcallback.client.serverreference.SOAPPull;

public class SOAPPullServiceClient {

    public static void main(String[] args) {
        
        try { // Call Web Service Operation
            
        	
        	JaxWsProxyFactoryBean  factory = new JaxWsProxyFactoryBean();
            factory.setServiceClass(SOAPPull.class);
            factory.setAddress("http://localhost:8080/soap/nomeinterfacciaservizio/v1");
            BindingConfiguration config = new BindingConfiguration() {

	            @Override
	            public String getBindingId() {
	                    
	                    return javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING; 
	            }
            };
            factory.setBindingConfig(config);
            
            SOAPPull port = (SOAPPull)factory.create();
        	
        	MRequest parameters = new MRequest();
        	MType m = new MType();
            m.setOId(1234);
            
            AComplexType a = new AComplexType();
            a.getA1S().add("1");
            a.setA2("prova");
            m.setA(a);
            m.setB("prova");
            parameters.setM(m);
            
            Holder<MRequestResponse> result = new Holder<>();
            Holder<java.lang.String> xCorrelationID = new Holder<>();
            port.mRequest(parameters, result, xCorrelationID);
            
            while (true) {
                try {
                    MProcessingStatus parameters2 = new MProcessingStatus();
                    MProcessingStatusResponse result2 = port.mProcessingStatus(parameters2, xCorrelationID.value);
                    System.out.println(result2.getReturn().getStatus());
                    if (result2.getReturn().getStatus().equals("done")) {
                        break;
                    }
                } catch (ErrorMessageException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
                Thread.sleep(1000);
            }
            
            try { 
                MResponse parameters3 = new MResponse();
                MResponseResponse result3 = port.mResponse(parameters3, xCorrelationID.value);
                System.out.println("Result = "+result3.getReturn().getC());
            } catch (Exception ex) {
            	ex.printStackTrace();
            }

        } catch (Exception ex) {
        	ex.printStackTrace();
        }

    }
}
