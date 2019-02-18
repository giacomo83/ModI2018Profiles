package modi2018.restcallback.server;

/*import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;*/
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.callbacks.Callback;
import io.swagger.v3.oas.annotations.callbacks.Callbacks;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import modi2018.restcallback.MType;
import modi2018.restcallback.ACKMessage;
import modi2018.restcallback.ErrorMessage;
import modi2018.restcallback.MResponseType;

@Path("/")
public class RESTCallbackImpl {

    @POST
    @Path("/resources/{id_resource}/M")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    /*@ApiOperation(value = "M", response = ACKMessage.class)
        @ApiResponses(value= {
            @ApiResponse(code = 202, message = "Preso carico correttamente di M", response = ACKMessage.class),
            @ApiResponse(code = 500, message = "Errore interno avvenuto", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Identificativo non trovato", response = ErrorMessage.class)
        })*/
    @Operation(description = "M",
            responses = {
                @ApiResponse(responseCode = "500", description = "Errore interno avvenuto", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
                ,
                @ApiResponse(responseCode = "404", description = "Identificativo non trovato", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
                ,
                @ApiResponse(responseCode = "200", description = "Preso carico correttamente di M",
                        content = @Content(schema = @Schema(implementation = ACKMessage.class)),
                        headers = {
                            @Header(name = "X-Correlation-ID", required = true, schema = @Schema(implementation = String.class))})
            })
    @Callbacks(value = {
        @Callback(name = "completionCallback", callbackUrlExpression = "{$request.header#/X-ReplyTo}", operation
                = @Operation(method = "post", responses = {
            @ApiResponse(responseCode = "202", description = "Ricevuto", content = @Content(schema = @Schema(implementation = ACKMessage.class)))
        }, requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = MResponseType.class))))
        )
    })
    public Response PushMessage(@HeaderParam("X-ReplyTo") String replyTo,
            MType M, @PathParam("id_resource") int id_resource) {
        final String guid = UUID.randomUUID().toString();
        ProcessingThread pt = new ProcessingThread(guid, replyTo, M, id_resource);
        new Thread(pt).run();
        return Response.status(200).entity(new ACKMessage("ACK")).header("X-Correlation-ID", guid).build();
    }
}
