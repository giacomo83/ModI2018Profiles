package modi2018.restcallback.server;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import modi2018.restcallback.ACKMessage;
import modi2018.restcallback.MResponseType;
import modi2018.restcallback.MType;

public class ProcessingThread implements Runnable {
	private MType toProcess;
	private String correlationId;
        private int idResource;
        private String replyTo;
	public ProcessingThread(String correlationId, String replyTo, MType toProcess, int idResource) {
		this.toProcess = toProcess;
		this.correlationId = correlationId;
                this.idResource = idResource;
                this.replyTo = replyTo;
	}
	public void run() {
		try {
			Thread.sleep(5000);
			try {
				URL url = new URL(replyTo);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("X-Correlation-ID", correlationId);
				MResponseType resp = new MResponseType();
				resp.c = "OK";
				ObjectMapper om = new ObjectMapper();
				OutputStream os = conn.getOutputStream();
				os.write(om.writeValueAsString(resp).getBytes());
				os.flush();
				if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
					throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
				}
				ObjectReader or = om.readerFor(ACKMessage.class);
				ACKMessage ack = or.readValue(conn.getInputStream());
				System.out.println(ack.outcome);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
