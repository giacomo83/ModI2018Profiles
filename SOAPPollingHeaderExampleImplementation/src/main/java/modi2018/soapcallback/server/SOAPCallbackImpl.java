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
import modi2018.soapcallback.ProcessingStatus;
import modi2018.soapcallback.MResponseType;

public class SOAPCallbackImpl implements SOAPCallback {
    @Resource
    private WebServiceContext webServiceContext;
    
    private static final HashMap<String, ProcessingStatus> processingStatuses = new HashMap<>();
    private static final HashMap<String, MResponseType> results = new HashMap<>();

    @Override
    public ProcessingStatus PushMessage(@WebParam(name = "M") MType M,
            @WebParam(name="X-CorrelationID", header=true, mode=WebParam.Mode.OUT) Holder<String> correlationID) throws ErrorMessageException {
        final String guid = UUID.randomUUID().toString();
        final ProcessingStatus mps = new ProcessingStatus();
        mps.status = "pending";
        mps.message = "Preso carico della richiesta";
        synchronized (processingStatuses) {
            processingStatuses.put(guid, mps);
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                synchronized(mps) {
                    mps.status = "processing";
                    mps.message = "Richiesta in fase di processamento";
                }
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
                synchronized(mps) {
                    mps.status = "done";
                    mps.message = "Processamento completo";
                }
            }
        }.start();
        correlationID.value = guid;
        return mps;
    }

    @Override
    public ProcessingStatus ResponseMessageById(String correlationID) throws ErrorMessageException {
        if (!processingStatuses.containsKey(correlationID)) {
            throw new ErrorMessageException("10", "Identificativo richiesta non trovato");
        }
        ProcessingStatus mps = processingStatuses.get(correlationID);
        return mps;
    }

    @Override
    public MResponseType ResponseById(String correlationID) throws ErrorMessageException {
        if (!processingStatuses.containsKey(correlationID)) {
            throw new ErrorMessageException("10", "Identificativo richiesta non trovato");
        }
        if (!results.containsKey(correlationID)) {
            throw new ErrorMessageException("11", "Esito richiesta non ancora disponibile");
        }
        MResponseType mps = results.get(correlationID);
        return mps;
    }
}
