/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modi2018.soapcallback.client;

/**
 *
 * @author Francesco
 */
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;

import modi2018.soapcallback.MResponseType;

@WebService(targetNamespace = "http://amministrazioneesempio.it/nomeinterfacciaservizio", portName = "SOAPCallbackPort", name = "SOAPCallbackPort" )
@Addressing(enabled = true, required = false)
public interface SOAPCallbackClientInterface {

    String NS = "http://amministrazioneesempio.it/nomeinterfacciaservizio";

    @Oneway
    @WebMethod(operationName = "MRequestResponse", action="http://amministrazioneesempio.it/nomeinterfacciaservizio/SOAPCallback/MRequestResponse")
    public void callBack(@WebParam(name = "return") MResponseType callbackMessage);

}
