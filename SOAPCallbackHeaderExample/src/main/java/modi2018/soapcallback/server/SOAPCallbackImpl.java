package modi2018.soapcallback.server;

import java.util.UUID;

import javax.annotation.Resource;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.BindingType;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceContext;

import modi2018.soapcallback.client.serverreference.AckMessage;
import modi2018.soapcallback.client.serverreference.ErrorMessageException;
import modi2018.soapcallback.client.serverreference.MRequest;
import modi2018.soapcallback.client.serverreference.MRequestResponse;
import modi2018.soapcallback.client.serverreference.SOAPCallback;



@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class SOAPCallbackImpl implements SOAPCallback {
	
	static int count=0;
	
    @Resource
    private WebServiceContext webServiceContext;

 

	@Override
	public void mRequest(MRequest parameters, String xReplyTo, Holder<MRequestResponse> result,Holder<String> xCorrelationID) throws ErrorMessageException {
		
		
		 final String guid = UUID.randomUUID().toString();
	     System.out.println("Generated X-Correlation-ID "+guid+" for req"+(++count));
	     
	     ThreadLocal<Integer> countCatch=new ThreadLocal<>();
	     countCatch.set(count);
	     
	     AckMessage ack=new AckMessage();
	     ack.setOutcome("ACCEPTED");
	     
	     result.value=new MRequestResponse();
	     result.value.setReturn(ack);
	     xCorrelationID.value=guid;   
	        
	     Responder responder=new Responder(countCatch.get(),xReplyTo,guid);
	        
	     Thread t = new Thread(responder);
		
		 t.start();
		
	}
	
	
	class Responder implements Runnable{

		private Integer reqId;
		private String xReplyTo;
		private String guid;
		
		



		public Responder(Integer reqId, String xReplyTo, String guid) {
			super();
			this.reqId = reqId;
			this.xReplyTo = xReplyTo;
			this.guid = guid;
		}





		@Override
		public void run() {
			try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            
            try { // Call Web Service Operation
                modi2018.soapcallback.server.clientreference.SOAPCallbackClientService service = new modi2018.soapcallback.server.clientreference.SOAPCallbackClientService();
                modi2018.soapcallback.server.clientreference.SOAPCallbackClient port = service.getSOAPCallbackClientPort();
              
                BindingProvider bindingProvider = (BindingProvider) port;
                bindingProvider.getRequestContext().put(
                        BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                        xReplyTo);
                
                // TODO initialize WS operation arguments here
                modi2018.soapcallback.server.clientreference.MRequestResponse parameters = new modi2018.soapcallback.server.clientreference.MRequestResponse();
                modi2018.soapcallback.server.clientreference.MResponseType m = new modi2018.soapcallback.server.clientreference.MResponseType();
                m.setC((reqId%2==0)?"KO":"OK");
                System.out.println("Computation Response = "+m.getC()+" for req"+reqId+" and xCorrelationID="+guid);
                parameters.setReturn(m);
                java.lang.String xCorrelationID = guid;
                // TODO process result here
                modi2018.soapcallback.server.clientreference.MRequestResponseResponse result = port.mRequestResponse(parameters, xCorrelationID);
                System.out.println("Result = "+result.getReturn().getOutcome());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("Replay TO:"+xReplyTo);
			
		}
		
		
	}
	
	
}
