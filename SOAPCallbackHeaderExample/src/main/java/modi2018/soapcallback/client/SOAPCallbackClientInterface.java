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
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import modi2018.soapcallback.ACKMessage;
import modi2018.soapcallback.MResponseType;

@WebService(targetNamespace = "http://amministrazioneesempio.it/nomeinterfacciaservizio", portName = "SOAPCallbackPort", name = "SOAPCallbackPort" )
public interface SOAPCallbackClientInterface {

    String NS = "http://amministrazioneesempio.it/nomeinterfacciaservizio";

    @WebMethod(operationName = "MRequestResponse")
    public ACKMessage callBack(@WebParam(name = "return") MResponseType M,
            @WebParam(name="X-CorrelationID", header=true) String correlationID);

}
