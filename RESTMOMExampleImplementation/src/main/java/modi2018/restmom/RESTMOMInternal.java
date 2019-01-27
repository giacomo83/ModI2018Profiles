/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modi2018.restmom;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Francesco
 */
@Path("/")
public class RESTMOMInternal {
    @Context
    private HttpServletResponse restContext;
    
    @GET
    @Path("/resources/{id_resource}/M")
    @Produces(MediaType.APPLICATION_JSON)
    public MType PullMessage() {
        try {
            Message message = RESTMOMServer.consumer.receiveNoWait();
            if (message != null && message instanceof ObjectMessage) {
                ObjectMessage oMessage = (ObjectMessage) message;
                MType returnM = (MType) oMessage.getObject();
                restContext.addHeader("X-Correlation-ID", oMessage.getJMSCorrelationID());
                return returnM;
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    @POST
    @Path("M/{correlationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PushResponseMessageReturn PushResponseMessage(@HeaderParam("X-Correlation-ID") String correlationId, MResponseType M) {
        try {
            //restContext.addHeader("X-Correlation-ID", correlationId);
            Session session = RESTMOMServer.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(RESTMOMServer.destinationQueues.get(RESTMOMServer.correlationIdToClient.get(correlationId)));
            MessageProducer producer = session.createProducer(destination);
            ObjectMessage message = session.createObjectMessage(M);
            message.setJMSCorrelationID(correlationId);
            producer.send(message);
            return new PushResponseMessageReturn("ACK");
        } catch (JMSException e) {
            e.printStackTrace();
            return new PushResponseMessageReturn("NACK");
        }
    }
}
