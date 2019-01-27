package modi2018.soapblocking;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace = "http://amministrazioneesempio.it/nomeinterfacciaservizio")
public class SOAPBlockingImpl {
	
    @WebMethod(operationName="M")
    public MResponseType PushMessage(@WebParam(name = "M") MType M) throws ErrorMessageException {
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
