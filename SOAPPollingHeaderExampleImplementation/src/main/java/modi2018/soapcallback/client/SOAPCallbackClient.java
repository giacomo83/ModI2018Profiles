package modi2018.soapcallback.client;

import java.net.URL;

public class SOAPCallbackClient {

    public static void main(String[] args) {
        
        try { // Call Web Service Operation
            modi2018.soapcallback.client.serverreference.SOAPCallbackService service = new modi2018.soapcallback.client.serverreference.SOAPCallbackService(new URL("http://localhost:8080/soap/nomeinterfacciaservizio/v1?wsdl"));
            modi2018.soapcallback.client.serverreference.SOAPCallback port = service.getSOAPCallbackPort();
            
            modi2018.soapcallback.client.serverreference.MRequest parameters = new modi2018.soapcallback.client.serverreference.MRequest();
            modi2018.soapcallback.client.serverreference.MType m = new modi2018.soapcallback.client.serverreference.MType();
            m.setOId(1234);
            modi2018.soapcallback.client.serverreference.AComplexType a = new modi2018.soapcallback.client.serverreference.AComplexType();
            a.getA1S().add("1");
            a.setA2("prova");
            m.setA(a);
            m.setB("prova");
            parameters.setM(m);
            
            javax.xml.ws.Holder<modi2018.soapcallback.client.serverreference.MRequestResponse> result = new javax.xml.ws.Holder<>();
            javax.xml.ws.Holder<java.lang.String> xCorrelationID = new javax.xml.ws.Holder<>();
            port.mRequest(parameters, result, xCorrelationID);
            
            while (true) {
                try {
                    modi2018.soapcallback.client.serverreference.MNextResponse parameters2 = new modi2018.soapcallback.client.serverreference.MNextResponse();
                    javax.xml.ws.Holder<modi2018.soapcallback.client.serverreference.MNextResponseResponse> result2 = new javax.xml.ws.Holder<>();
                    javax.xml.ws.Holder<java.lang.String> xCorrelationID2 = new javax.xml.ws.Holder<>();
                    port.mNextResponse(parameters2, result2, xCorrelationID2);
                    System.out.println("Result received for " + xCorrelationID2.value);
                    break;
                } catch (modi2018.soapcallback.client.serverreference.ErrorMessageException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
                Thread.sleep(1000);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }

    }
}
