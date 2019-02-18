package modi2018.restcallback.client;

/*import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;*/
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import modi2018.restcallback.ACKMessage;
import modi2018.restcallback.ErrorMessage;
import modi2018.restcallback.MResponseType;

@Path("/")
public class RESTCallbackClientImpl {
	@POST
	@Path("MResponse")
	@Produces(MediaType.APPLICATION_JSON)
        /*@ApiOperation(value = "MResponse", response = ACKMessage.class)
        @ApiResponses(value= {
            @ApiResponse(code = 200, message = "Risposta correttamente ricevuta", response = ACKMessage.class),
            @ApiResponse(code = 500, message = "Errore interno avvenuto", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Correlation ID non corretto", response = ErrorMessage.class)
        })*/
        @Operation(description="M",
            responses= {
                @ApiResponse(responseCode = "500", description = "Errore interno avvenuto", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "404", description = "Identificativo non trovato", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "202", description = "Risposta correttamente ricevuta")
            })
	public Response PushResponseMessage(@HeaderParam("X-Correlation-ID") String correlationId, MResponseType response) {
		return Response.status(202).build();
	}
}
