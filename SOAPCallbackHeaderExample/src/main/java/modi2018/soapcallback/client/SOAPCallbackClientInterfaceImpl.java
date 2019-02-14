/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modi2018.soapcallback.client;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import modi2018.soapcallback.ACKMessage;
import modi2018.soapcallback.MResponseType;
import org.apache.cxf.ws.addressing.AddressingProperties;

public class SOAPCallbackClientInterfaceImpl implements SOAPCallbackClientInterface {
    
    @Resource
    private WebServiceContext webServiceContext;

    @Override
    public ACKMessage callBack(@WebParam(name = "return") MResponseType M,
            @WebParam(name="X-CorrelationID", header=true) String correlationID) {
        System.out.println("Recieved callback message " + correlationID);
        ACKMessage returnValue = new ACKMessage();
        returnValue.outcome = "ACK";
        return returnValue;
    }
    
}
