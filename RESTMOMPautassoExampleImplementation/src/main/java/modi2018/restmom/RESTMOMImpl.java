package modi2018.restmom;

/*import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;*/
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.HashMap;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class RESTMOMImpl {

    @Context
    private HttpServletResponse restContext;
    
    private static final HashMap<String, MResponseType> results = new HashMap<>();
    private static final HashMap<String, MProcessingStatus> processingStatuses = new HashMap<>();

    @POST
    @Path("/resources/{id_resource}/M")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description="M",
            responses= {
                @ApiResponse(responseCode = "500", description = "Errore interno avvenuto", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "404", description = "Identificativo non trovato", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "202", description = "Preso carico correttamente di M",
                        content=@Content(schema=@Schema(implementation=MProcessingStatus.class)),
                        headers={@Header(name="Location", required = true, description="Posizione nella quale richiedere lo stato della richiesta", schema=@Schema(implementation=String.class))})
            })
    public Response PushMessage(MType M, @PathParam("id_resource") int id_resource) {
        final String guid = UUID.randomUUID().toString();
        final MProcessingStatus mps = new MProcessingStatus();
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
                    mps.status = "Processamento completo";
                }
            }
        }.start();
        return Response.status(202).entity(mps).header("Location", "resources/" + id_resource + "/M/" + guid).build();
    }
    
    @GET
    @Path("/resources/{id_resource}/M/{id_task}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description="M Processing Status",
            responses= {
                @ApiResponse(responseCode = "500", description = "Errore interno avvenuto", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "404", description = "Identificativo non trovato", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "200", description = "Esecuzione di M completata", content=@Content(schema=@Schema(implementation=MProcessingStatus.class))),
                @ApiResponse(responseCode = "303", description = "Preso carico correttamente di M",
                        content=@Content(schema=@Schema(implementation=MProcessingStatus.class)),
                        headers={@Header(name="Location", required = true, description="Posizione nella quale richiedere l'esito della richiesta", schema=@Schema(implementation=String.class))})
                })
    public Response PullResponseStatusById(@PathParam("id_resource") int id_resource,
            @PathParam("id_task") String id_task) {
        if (!processingStatuses.containsKey(id_task)) {
            return Response.status(404).entity(new ErrorMessage("Identificativo richiesta non trovato")).build();
        }
        MProcessingStatus mps = processingStatuses.get(id_task);
        if (results.containsKey(id_task)) {
            return Response.status(303).entity(mps).header("Location", "resources/" + id_resource + "/M/" + id_task + "/result").build();
        } else {
            return Response.status(200).entity(mps).build();
        }
    }
    
    @GET
    @Path("/resources/{id_resource}/M/{id_task}/result")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description="M Result",
            responses= {
                @ApiResponse(responseCode = "500", description = "Errore interno avvenuto", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "404", description = "Identificativo non trovato", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "200", description = "Esecuzione di M completata", content=@Content(schema=@Schema(implementation=MResponseType.class)))
            })
    public Response PullResponseById(@PathParam("id_resource") int id_resource,
            @PathParam("id_task") String id_task) {
        if (!processingStatuses.containsKey(id_task)) {
            return Response.status(404).entity(new ErrorMessage("Identificativo richiesta non trovato")).build();
        }
        if (!results.containsKey(id_task)) {
            return Response.status(404).entity(new ErrorMessage("Esito richiesta non ancora disponibile")).build();
        }
        MResponseType mps = results.get(id_task);
        return Response.status(200).entity(mps).build();
    }
}
