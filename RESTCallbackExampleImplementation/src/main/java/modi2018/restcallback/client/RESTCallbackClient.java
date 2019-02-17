package modi2018.restcallback.client;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import modi2018.restcallback.ACKMessage;
import modi2018.restcallback.AComplexType;
import modi2018.restcallback.MType;
import org.apache.cxf.jaxrs.openapi.OpenApiFeature;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;

public class RESTCallbackClient {
	public static void main(String[] args) {
            Swagger2Feature feature = new Swagger2Feature();
            feature.setBasePath("/rest/v1/nomeinterfacciaservizio");
            feature.setScanAllResources(true);
            feature.setPrettyPrint(true);
            feature.setVersion("1.0");
            feature.setTitle("RESTcallback");
            
            OpenApiFeature openapifeature = new OpenApiFeature();
        openapifeature.setScan(true);
        openapifeature.setPrettyPrint(true);
        openapifeature.setVersion("1.0");
        openapifeature.setTitle("RESTcallback");
            
		JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
                factoryBean.getFeatures().add(feature);
                factoryBean.getFeatures().add(openapifeature);
		List<Object> providers = new ArrayList<Object>();
		providers.add(new JacksonJaxbJsonProvider());
		factoryBean.setProviders(providers);
	    factoryBean.setResourceClasses(RESTCallbackClientImpl.class);
	    factoryBean.setResourceProvider(new SingletonResourceProvider(new RESTCallbackClientImpl()));
	    factoryBean.setAddress("http://localhost:8181/rest/v1/nomeinterfacciaservizio");
	    factoryBean.create();
	    
	    try {
		    URL url = new URL("http://localhost:8080/rest/v1/nomeinterfacciaservizio/resources/" + 1234 + "/M");
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
                        conn.setRequestProperty("X-ReplyTo", "http://localhost:8181/rest/v1/nomeinterfacciaservizio/MResponse");
			MType req = new MType();
			AComplexType a = new AComplexType();
			a.a1s = new LinkedList<Integer>();
			a.a1s.add(1);
			a.a2 = "prova";
			req.a = a;
			req.b = "prova";
			ObjectMapper om = new ObjectMapper();
			OutputStream os = conn.getOutputStream();
			System.out.println(om.writeValueAsString(req));
			os.write(om.writeValueAsString(req).getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_ACCEPTED) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}
			ObjectReader or = om.readerFor(ACKMessage.class);
			ACKMessage ack = or.readValue(conn.getInputStream());
			System.out.println(ack.outcome);
	    
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
