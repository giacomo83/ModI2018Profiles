package modi2018.soapblocking;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;

@WebService(targetNamespace = "http://amministrazioneesempio.it/nomeinterfacciaservizio")
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class SOAPBlockingImpl {
	
    @WebMethod(operationName="MRequest")
    public MResponseType PushMessage(@WebParam(name="M") MType M) throws ErrorMessageException {
                if (M.oId == -1) {
                    ErrorMessageFault emf = new ErrorMessageFault();
                    emf.setCustomFaultCode("1234");
                    throw new ErrorMessageException("Error", emf);
                }
		MResponseType returnValue = new MResponseType();
		returnValue.c = "OK";
		return returnValue;
	}
    

}
