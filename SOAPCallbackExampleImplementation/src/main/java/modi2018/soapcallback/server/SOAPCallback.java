/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modi2018.soapcallback.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;
import modi2018.soapcallback.ErrorMessageException;
import modi2018.soapcallback.MResponseType;
import modi2018.soapcallback.MType;

/**
 *
 * @author Francesco
 */
@WebService(targetNamespace = "http://amministrazioneesempio.it/nomeinterfacciaservizio")
@Addressing(enabled = true, required = true)
public interface SOAPCallback {
    @WebMethod(operationName="MRequest")
    public MResponseType PushMessage(@WebParam(name = "M") MType M) throws ErrorMessageException;
    //public void OneWayTest(@WebParam(name = "M") MType M);
}
