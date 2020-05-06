package modi2018.soapcallback.server;

import java.util.HashMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.xml.ws.BindingType;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceContext;

import modi2018.soapcallback.client.serverreference.ErrorMessageException;
import modi2018.soapcallback.client.serverreference.ErrorMessageFault;
import modi2018.soapcallback.client.serverreference.MProcessingStatus;
import modi2018.soapcallback.client.serverreference.MProcessingStatusResponse;
import modi2018.soapcallback.client.serverreference.MRequest;
import modi2018.soapcallback.client.serverreference.MRequestResponse;
import modi2018.soapcallback.client.serverreference.MResponse;
import modi2018.soapcallback.client.serverreference.MResponseResponse;
import modi2018.soapcallback.client.serverreference.MResponseType;
import modi2018.soapcallback.client.serverreference.ProcessingStatus;
import modi2018.soapcallback.client.serverreference.SOAPPull;

@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class SOAPPullServiceImpl implements SOAPPull {
    @Resource
    private WebServiceContext webServiceContext;
    
    private static final HashMap<String, ProcessingStatus> processingStatuses = new HashMap<>();
    private static final HashMap<String, MResponseType> results = new HashMap<>();

  
	@Override
	public void mRequest(MRequest parameters, Holder<MRequestResponse> result, Holder<String> xCorrelationID) throws ErrorMessageException {
		
		  final String guid = UUID.randomUUID().toString();
	      final ProcessingStatus mps = new ProcessingStatus();
	      mps.setStatus("accepted");
	      mps.setMessage("Preso carico della richiesta");
	        
	      synchronized (processingStatuses) {
	            processingStatuses.put(guid, mps);
	      }
	        
          xCorrelationID.value = guid;
          result.value=new MRequestResponse();
          result.value.setReturn(mps);
          
          Thread requestExecutor=new Thread(new RequestExecutor(guid));
	      requestExecutor.start();
	        
	        
	}

	@Override
	public MProcessingStatusResponse mProcessingStatus(MProcessingStatus parameters, String xCorrelationID) throws ErrorMessageException {
		
		synchronized (processingStatuses) {
		
			checkValidRequest(xCorrelationID);
			
        	MProcessingStatusResponse mpsResp=new MProcessingStatusResponse();
        	mpsResp.setReturn(processingStatuses.get(xCorrelationID));
        	
        	return mpsResp;
       
		}
		
	}

	@Override
	public MResponseResponse mResponse(MResponse parameters, String xCorrelationID) throws ErrorMessageException {
		
		checkValidRequest(xCorrelationID);
		
		synchronized (results) {
			if (!results.containsKey(xCorrelationID)) {
				ErrorMessageFault faultInfo=new ErrorMessageFault();
			    faultInfo.setCustomFaultCode("11");
	            throw new ErrorMessageException("Esito richiesta non ancora disponibile",faultInfo);
	        }
			
			MResponseResponse resp=new MResponseResponse();
			resp.setReturn(results.get(xCorrelationID));
			
	        return resp;
		}
		
	}
	
	private void checkValidRequest(String xCorrelationID) throws ErrorMessageException {
		
		synchronized (processingStatuses) {
			
			if (!processingStatuses.containsKey(xCorrelationID)) {
			    ErrorMessageFault faultInfo=new ErrorMessageFault();
			    faultInfo.setCustomFaultCode("10");
				throw new ErrorMessageException("Identificativo richiesta non trovato",faultInfo);
			}
		}
		
	}
	
	
	class RequestExecutor implements Runnable {

		private String guid;
		
		public RequestExecutor(String guid) {
			super();
			this.guid = guid;
		}

		@Override
		public void run() {

			try {
				Thread.sleep(5000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			doProcessing();

			try {
				Thread.sleep(5000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			doDone();

		}

		private void doProcessing() {

			synchronized (processingStatuses.get(guid)) {
				processingStatuses.get(guid).setStatus("processing");
				processingStatuses.get(guid).setMessage("Richiesta in fase di processamento");
				
			}
			
		}

		private void doDone() {

			MResponseType m = new MResponseType();
            m.setC("OK");
            synchronized(results) {
                results.put(guid, m);
            }
			
			synchronized (processingStatuses.get(guid)) {
				processingStatuses.get(guid).setStatus("done");
				processingStatuses.get(guid).setMessage("Richiesta completata");
				
			}
		}

	}
}
