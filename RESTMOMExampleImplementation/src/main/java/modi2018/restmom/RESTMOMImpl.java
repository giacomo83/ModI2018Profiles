package modi2018.restmom;

/*import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;*/
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.UUID;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class RESTMOMImpl {

    @Context
    private HttpServletResponse restContext;

    @POST
    @Path("/resources/{id_resource}/M")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description="M",
            responses= {
                @ApiResponse(responseCode = "500", description = "Errore interno avvenuto", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "404", description = "Identificativo non trovato", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "202", description = "Preso carico correttamente di M",
                        content=@Content(schema=@Schema(implementation=PushMessageReturn.class)),
                        headers={@Header(name="X-Correlation-ID", required=true, schema=@Schema(implementation=String.class))})
            })
    /*@ApiOperation(value = "M", response = PushMessageReturn.class)
        @ApiResponses(value= {
            @ApiResponse(code = 202, message = "Preso carico correttamente di M", response = PushMessageReturn.class),
            @ApiResponse(code = 500, message = "Errore interno avvenuto", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Identificativo non trovato", response = ErrorMessage.class)
        })*/
    public Response PushMessage(/*@QueryParam("clientId") String clientId, */MType M, @PathParam("id_resource") int id_resource) {
        String clientId = "testQueue";
        try {
            //restContext.addHeader("X-Correlation-ID", correlationId);
            Session session = RESTMOMServer.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("MQUEUE");
            MessageProducer producer = session.createProducer(destination);
            ObjectMessage message = session.createObjectMessage(M);
            message.setStringProperty("clientId", clientId);
            final String guid = UUID.randomUUID().toString();
            message.setJMSCorrelationID(guid);
            Queue replyToQueue = null;
            if (RESTMOMServer.destinationQueues.containsKey(clientId)) {
                replyToQueue = session.createQueue(RESTMOMServer.destinationQueues.get(clientId));
            } else  {
                replyToQueue = session.createTemporaryQueue();
                RESTMOMServer.destinationQueues.put(clientId, replyToQueue.getQueueName());
            }
            message.setJMSReplyTo(replyToQueue);
            producer.send(message);
            RESTMOMServer.correlationIdToClient.put(guid, clientId);
            return Response.status(202).entity(new PushMessageReturn("ACK")).header("X-Correlation-ID", guid).build();
        } catch (JMSException e) {
            e.printStackTrace();
            return Response.status(500).entity(new ErrorMessage("NACK")).build();
        }
    }

    @GET
    @Path("MNextResponse")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description="MResponse",
            responses= {
                @ApiResponse(responseCode = "500", description = "Errore interno avvenuto", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "404", description = "Identificativo non trovato", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "200", description = "Esecuzione di M completata", content=@Content(schema=@Schema(implementation=MResponseType.class)),
                        headers={
                    @Header(name="X-Correlation-ID", description="Correlation ID", schema=@Schema(implementation=String.class))
                })
            })
    /*@ApiOperation(value = "MResponse", response = MResponseType.class)
        @ApiResponses(value= {
            @ApiResponse(code = 200, message = "Esecuzione di M completata", response = MResponseType.class),
            @ApiResponse(code = 500, message = "Errore interno avvenuto", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Nessun messaggio trovato", response = ErrorMessage.class)
        })*/
    public Response PullNextResponseMessage(/*@QueryParam("clientId") String clientId*/) {
        String clientId = "testQueue";
        try {
            Session session = RESTMOMServer.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(RESTMOMServer.destinationQueues.get(clientId));
            MessageConsumer consumer = session.createConsumer(destination);
            Message message = consumer.receiveNoWait();
            if (message != null && message instanceof ObjectMessage) {
                ObjectMessage oMessage = (ObjectMessage) message;
                MResponseType returnM = (MResponseType) oMessage.getObject();
                //restContext.addHeader("X-Correlation-ID", oMessage.getJMSCorrelationID());
                return Response.status(200).entity(returnM).header("X-Correlation-ID", oMessage.getJMSCorrelationID()).build();
            } else {
                return Response.status(404).build();
            }
        } catch (JMSException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }
    
    @GET
    @Path("MResponseById")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description="MResponse",
            responses= {
                @ApiResponse(responseCode = "500", description = "Errore interno avvenuto", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "404", description = "Identificativo non trovato", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "200", description = "Esecuzione di M completata", content=@Content(schema=@Schema(implementation=MResponseType.class)))
                })
    /*@ApiOperation(value = "MResponse", response = MResponseType.class)
        @ApiResponses(value= {
            @ApiResponse(code = 200, message = "Esecuzione di M completata", response = MResponseType.class),
            @ApiResponse(code = 500, message = "Errore interno avvenuto", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Nessun messaggio trovato", response = ErrorMessage.class)
        })*/
    public Response PullResponseMessageById(/*@QueryParam("clientId") String clientId, */@HeaderParam("X-Correlation-ID") String correlationId) {
        String clientId = "testQueue";
        try {
            Session session = RESTMOMServer.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(RESTMOMServer.destinationQueues.get(clientId));
            MessageConsumer consumer = session.createConsumer(destination);
            //TODO: Search by Id
            Message message = consumer.receiveNoWait();
            if (message != null && message instanceof ObjectMessage) {
                ObjectMessage oMessage = (ObjectMessage) message;
                MResponseType returnM = (MResponseType) oMessage.getObject();
                return Response.status(200).entity(returnM).build();
            } else {
                return Response.status(404).build();
            }
        } catch (JMSException e) {
            e.printStackTrace();
            return Response.status(500).build();
        }
    }

}
