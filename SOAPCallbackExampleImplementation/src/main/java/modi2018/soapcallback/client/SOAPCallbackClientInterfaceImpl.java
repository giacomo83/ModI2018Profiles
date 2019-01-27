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
import modi2018.soapcallback.MResponseType;
import org.apache.cxf.ws.addressing.AddressingProperties;

public class SOAPCallbackClientInterfaceImpl implements SOAPCallbackClientInterface {
    
    @Resource
    private WebServiceContext webServiceContext;

    @Override
    public void callBack(@WebParam(name="return") MResponseType callbackMessage) {
        MessageContext messageContext = webServiceContext.getMessageContext();

        AddressingProperties addressProp = (AddressingProperties) messageContext
                .get(org.apache.cxf.ws.addressing.JAXWSAConstants.ADDRESSING_PROPERTIES_INBOUND);
        System.out.println("Received callback message " + callbackMessage.c + " for message ID " + addressProp.getRelatesTo().getValue());
        //System.out.println("Recieved callback message " + callbackMessage.c);
    }
    
}
