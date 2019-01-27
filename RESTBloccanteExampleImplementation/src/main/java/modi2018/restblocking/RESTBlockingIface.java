/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modi2018.restblocking;

/*import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;*/
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Francesco
 */
@Path("/")
public interface RESTBlockingIface {
    @POST
	@Path("/resources/{id_resource}/M")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
        @Operation(description="M",
            responses= {
                @ApiResponse(responseCode = "500", description = "Errore interno avvenuto", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "404", description = "Identificativo non trovato", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "200", description = "Esecuzione di M avvenuta con successo", content=@Content(schema=@Schema(implementation=MResponseType.class)))
            })
        /*@ApiOperation(value = "M", response = MResponseType.class)
        @ApiResponses(value= {
            @ApiResponse(code = 200, message = "Esecuzione di M con successo", response = MResponseType.class),
            @ApiResponse(code = 500, message = "Errore interno avvenuto", response = ErrorMessage.class),
            @ApiResponse(code = 404, message = "Identificativo non trovato", response = ErrorMessage.class)
        })*/
	public Response PushMessage(MType M, @PathParam("id_resource") int id_resource);
}
