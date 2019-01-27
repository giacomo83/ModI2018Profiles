/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modi2018.restcrud;

/*import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;*/
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.HeaderParam;
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
    @Path("/municipio/{id_municipio}/ufficio/{id_ufficio}/prenotazioni")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description="AggiungiPrenotazione",
            responses= {
                @ApiResponse(responseCode = "201", headers={
                    @Header(name="Location", description="ID della prenotazione creata", schema=@Schema(implementation=String.class))
                }),
                @ApiResponse(responseCode = "500", description = "Errore interno avvenuto", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "404", description = "Identificativo non trovato", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "400", description = "Richiesta non accoglibile", content=@Content(schema=@Schema(implementation=ErrorMessage.class)))
            })
    /*@ApiOperation(value = "AggiungiPrenotazione", response = Prenotazione.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Prenotazione correttamente creata", responseHeaders = {@ResponseHeader(name="Location", description="ID della prenotazione creata", response=Integer.class  )})
        ,
            @ApiResponse(code = 500, message = "Errore interno avvenuto", response = ErrorMessage.class)
        ,
            @ApiResponse(code = 404, message = "Identificativo non trovato", response = ErrorMessage.class)
        ,
            @ApiResponse(code = 400, message = "Richiesta non accoglibile", response = ErrorMessage.class)
    })*/
    public Response AddReservation(Prenotazione p, @HeaderParam("X-Correlation-ID") String correlationId,
            @PathParam("id_municipio") int idMunicipio, @PathParam("id_ufficio") int idUfficio);
    
    @DELETE
    @Path("/municipio/{id_municipio}/ufficio/{id_ufficio}/prenotazioni/{id_prenotazione}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description="EliminaPrenotazione",
            responses= {
                @ApiResponse(responseCode = "500", description = "Errore interno avvenuto", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "404", description = "Identificativo non trovato", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "200", description = "Prenotazione eliminata correttamente", content=@Content(schema=@Schema(implementation=Prenotazione.class)))
            })
    /*@ApiOperation(value = "EliminaPrenotazione", response = Prenotazione.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Prenotazione eliminata correttamente")
        ,
            @ApiResponse(code = 500, message = "Errore interno avvenuto", response = ErrorMessage.class)
        ,
            @ApiResponse(code = 404, message = "Identificativo non trovato", response = ErrorMessage.class)
    })*/
    public Response DeleteReservation(@PathParam("id_municipio") int idMunicipio, @PathParam("id_ufficio") int idUfficio,
            @PathParam("id_prenotazione") int idPrenotazione);
    
    @PATCH
    @Path("/municipio/{id_municipio}/ufficio/{id_ufficio}/prenotazioni/{id_prenotazione}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description="ModificaPrenotazione",
            responses= {
                @ApiResponse(responseCode = "500", description = "Errore interno avvenuto", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "404", description = "Identificativo non trovato", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "200", description = "Prenotazione modificata correttamente", content=@Content(schema=@Schema(implementation=Prenotazione.class)))
            })
    /*@ApiOperation(value = "ModificaPrenotazione", response = Prenotazione.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Prenotazione eliminata correttamente")
        ,
            @ApiResponse(code = 500, message = "Errore interno avvenuto", response = ErrorMessage.class)
        ,
            @ApiResponse(code = 404, message = "Identificativo non trovato", response = ErrorMessage.class)
    })*/
    public Response PatchReservation(PatchPrenotazione p, @PathParam("id_municipio") int idMunicipio,
            @PathParam("id_ufficio") int idUfficio, @PathParam("id_prenotazione") int idPrenotazione);
    
    @GET
    @Path("/municipio/{id_municipio}/ufficio/{id_ufficio}/prenotazioni/{id_prenotazione}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description="LeggiPrenotazione",
            responses= {
                @ApiResponse(responseCode = "500", description = "Errore interno avvenuto", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "404", description = "Identificativo non trovato", content=@Content(schema=@Schema(implementation=ErrorMessage.class))),
                @ApiResponse(responseCode = "200", description = "Prenotazione estratta correttamente", content=@Content(schema=@Schema(implementation=Prenotazione.class)))
            })
    /*@ApiOperation(value = "LeggiPrenotazione", response = Prenotazione.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Prenotazione eliminata correttamente")
        ,
            @ApiResponse(code = 500, message = "Errore interno avvenuto", response = ErrorMessage.class)
        ,
            @ApiResponse(code = 404, message = "Identificativo non trovato", response = ErrorMessage.class)
    })*/
    public Response GetReservation(@PathParam("id_municipio") int idMunicipio, @PathParam("id_ufficio") int idUfficio,
            @PathParam("id_prenotazione") int idPrenotazione);
}
