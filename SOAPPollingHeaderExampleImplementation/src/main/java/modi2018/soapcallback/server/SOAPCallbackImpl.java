package modi2018.soapcallback.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import modi2018.soapcallback.MType;
import modi2018.soapcallback.ACKMessage;
import modi2018.soapcallback.ErrorMessageException;
import modi2018.soapcallback.MResponseType;

public class SOAPCallbackImpl implements SOAPCallback {
    @Resource
    private WebServiceContext webServiceContext;
    
    private static final HashMap<String, MResponseType> results = new HashMap<>();

    @Override
    public ACKMessage PushMessage(@WebParam(name = "M") MType M,
            @WebParam(name="X-CorrelationID", header=true, mode=WebParam.Mode.OUT) Holder<String> correlationID) throws ErrorMessageException {
        final String guid = UUID.randomUUID().toString();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                
                MResponseType m = new MResponseType();
                m.c = "OK";
                synchronized(results) {
                    results.put(guid, m);
                }
            }
        }.start();
        
        ACKMessage resp = new ACKMessage();
        resp.outcome = "ACCEPTED";
        correlationID.value = guid;
        return resp;
    }

    @Override
    public MResponseType PullResponseMessageById(String correlationID) throws ErrorMessageException {
        synchronized(results) {
            if (results.containsKey(correlationID)) {
                MResponseType toReturn = results.get(correlationID);
                results.remove(correlationID);
                return toReturn;
            } else {
                throw new ErrorMessageException("10", "No result found for " + correlationID);
            }
        }
    }

    @Override
    public MResponseType PullNextResponseMessage(@WebParam(name="X-CorrelationID", header=true, mode=WebParam.Mode.OUT) Holder<String> correlationID) throws ErrorMessageException {
        synchronized(results) {
            if (results.isEmpty()) {
                throw new ErrorMessageException("11", "No result available");
            }
            HashMap.Entry<String, MResponseType> entry = results.entrySet().iterator().next();
            results.remove(entry.getKey());
            correlationID.value = entry.getKey();
            return entry.getValue();
        }
    }
}
