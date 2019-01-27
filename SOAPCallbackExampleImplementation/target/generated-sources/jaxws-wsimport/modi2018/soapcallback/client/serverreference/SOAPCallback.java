
package modi2018.soapcallback.client.serverreference;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SOAPCallback", targetNamespace = "http://amministrazioneesempio.it/nomeinterfacciaservizio")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SOAPCallback {


    /**
     * 
     * @param m
     * @return
     *     returns modi2018.soapcallback.client.serverreference.MResponseType
     * @throws ErrorMessageException
     */
    @WebMethod(operationName = "M")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "M", targetNamespace = "http://amministrazioneesempio.it/nomeinterfacciaservizio", className = "modi2018.soapcallback.client.serverreference.M")
    @ResponseWrapper(localName = "MResponse", targetNamespace = "http://amministrazioneesempio.it/nomeinterfacciaservizio", className = "modi2018.soapcallback.client.serverreference.MResponse")
    @Action(input = "http://amministrazioneesempio.it/nomeinterfacciaservizio/SOAPCallback/MRequest", output = "http://amministrazioneesempio.it/nomeinterfacciaservizio/SOAPCallback/MResponse", fault = {
        @FaultAction(className = ErrorMessageException.class, value = "http://amministrazioneesempio.it/nomeinterfacciaservizio/SOAPCallback/M/Fault/ErrorMessageException")
    })
    public MResponseType m(
        @WebParam(name = "M", targetNamespace = "")
        MType m)
        throws ErrorMessageException
    ;

}
