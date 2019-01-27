package modi2018.soapcallback.server;

import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebParam;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.Addressing;

import modi2018.soapcallback.MType;
import modi2018.soapcallback.ACKMessage;
import modi2018.soapcallback.ErrorMessageException;
import modi2018.soapcallback.MResponseType;
import org.apache.cxf.annotations.UseAsyncMethod;
import org.apache.cxf.jaxws.ServerAsyncResponse;
import org.apache.cxf.ws.addressing.AddressingProperties;

public class SOAPCallbackImpl implements SOAPCallback {
    @Resource
    private WebServiceContext webServiceContext;
    
    /*public Response<MResponseType> PushMessageAsyncPolling(@WebParam(name = "M") MType M) {
        ServerAsyncResponse<MResponseType> r = new ServerAsyncResponse<MResponseType>();

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                MResponseType resp = new MResponseType();
                resp.c = "OK";
                r.set(resp);
                System.out.println("Responding on background thread\n");
            }
        }.start();
        return r;
    }
    
    public Future<?> PushMessageAsync(@WebParam(name = "M") MType M, AsyncHandler<MResponseType> asyncHandler) {
        ServerAsyncResponse<MResponseType> r = new ServerAsyncResponse<MResponseType>();

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                MResponseType resp = new MResponseType();
                resp.c = "OK";
                r.set(resp);
                System.out.println("Responding on background thread\n");
                asyncHandler.handleResponse(r);
            }
        }.start();
        return r;
    }*/

    //@UseAsyncMethod
    @Override
    public MResponseType PushMessage(@WebParam(name = "M") MType M) throws ErrorMessageException {
        MessageContext messageContext = webServiceContext.getMessageContext();

        /*AddressingProperties addressProp = (AddressingProperties) messageContext
            .get(org.apache.cxf.ws.addressing.JAXWSAConstants.ADDRESSING_PROPERTIES_INBOUND);
            ProcessingThread pt = new ProcessingThread(addressProp.getMessageID().getValue(), M, addressProp.getReplyTo().getAddress().getValue());
            new Thread(pt).start();*/
        
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SOAPCallbackImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        MResponseType resp = new MResponseType();
        resp.c = "OK";
        return resp;
    }

    /*@Override
    public void OneWayTest(MType M) {
        System.out.println("Ciao");
    }*/
}
