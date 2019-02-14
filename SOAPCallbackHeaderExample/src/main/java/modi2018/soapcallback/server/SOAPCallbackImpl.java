package modi2018.soapcallback.server;

import java.util.UUID;
import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import modi2018.soapcallback.MType;
import modi2018.soapcallback.ACKMessage;
import modi2018.soapcallback.ErrorMessageException;

public class SOAPCallbackImpl implements SOAPCallback {
    @Resource
    private WebServiceContext webServiceContext;

    @Override
    public ACKMessage PushMessage(@WebParam(name = "M") MType M,
            @WebParam(name="X-ReplyTo", header=true) String replyTo,
            @WebParam(name="X-CorrelationID", header=true, mode=WebParam.Mode.OUT) Holder<String> correlationID) throws ErrorMessageException {
        MessageContext messageContext = webServiceContext.getMessageContext();
        final String replyToF = replyTo;
        final String guid = UUID.randomUUID().toString();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                
                try { // Call Web Service Operation
                    modi2018.soapcallback.server.clientreference.SOAPCallbackClientInterfaceService service = new modi2018.soapcallback.server.clientreference.SOAPCallbackClientInterfaceService();
                    modi2018.soapcallback.server.clientreference.SOAPCallbackPort port = service.getSOAPCallbackPort();
                    // TODO initialize WS operation arguments here
                    modi2018.soapcallback.server.clientreference.MRequestResponse parameters = new modi2018.soapcallback.server.clientreference.MRequestResponse();
                    modi2018.soapcallback.server.clientreference.MResponseType m = new modi2018.soapcallback.server.clientreference.MResponseType();
                    m.setC("OK");
                    parameters.setReturn(m);
                    java.lang.String xCorrelationID = guid;
                    // TODO process result here
                    modi2018.soapcallback.server.clientreference.MRequestResponseResponse result = port.mRequestResponse(parameters, xCorrelationID);
                    System.out.println("Result = "+result.getReturn().getOutcome());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println(replyToF);
            }
        }.start();
        
        ACKMessage resp = new ACKMessage();
        resp.outcome = "ACCEPTED";
        correlationID.value = guid;
        return resp;
    }
}
