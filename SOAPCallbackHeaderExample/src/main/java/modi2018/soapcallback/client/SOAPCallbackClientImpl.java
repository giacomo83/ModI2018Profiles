
package modi2018.soapcallback.client;

import javax.annotation.Resource;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;

import modi2018.soapcallback.server.clientreference.AckMessage;
import modi2018.soapcallback.server.clientreference.MRequestResponse;
import modi2018.soapcallback.server.clientreference.MRequestResponseResponse;
import modi2018.soapcallback.server.clientreference.SOAPCallbackClient;

@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class SOAPCallbackClientImpl implements SOAPCallbackClient {
    
    @Resource
    private WebServiceContext webServiceContext;

   /* @Override
    public ACKMessage callBack(@WebParam(name = "return") MResponseType M,
            @WebParam(name="X-CorrelationID", header=true) String correlationID) {
        System.out.println("Recieved callback message " + correlationID);
        ACKMessage returnValue = new ACKMessage();
        returnValue.outcome = "ACK";
        return returnValue;
    }*/

	@Override
	public MRequestResponseResponse mRequestResponse(MRequestResponse parameters, String xCorrelationID) {
		
		System.out.println("Rececived Async Response with C="+parameters.getReturn().getC()+" and xCorrelationID="+xCorrelationID);
		
		
		
		MRequestResponseResponse resp=new MRequestResponseResponse();
		AckMessage ack = new AckMessage();	
		
		ack.setOutcome("OK");
		
		resp.setReturn(ack);
		
		return resp;
	}
    
}
