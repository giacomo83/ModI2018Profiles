package modi2018.restcrud;

import java.util.HashMap;

import javax.ws.rs.core.Response;

public class RESTBlockingImpl implements RESTBlockingIface {
    
    private static HashMap<Integer, Prenotazione> prenotazioni = new HashMap<>();
    private static HashMap<String, Integer> correlationIds = new HashMap<>();
    private static int currentIndex = 0;
   
    @Override
    public Response AddReservation(Prenotazione p, String correlationId, int idMunicipio, int idUfficio) {
        if (!correlationIds.containsKey(correlationId)) {
            prenotazioni.put(currentIndex, p);
            correlationIds.put(correlationId, currentIndex);
            currentIndex++;
        }
        return Response.status(202).header("Location", correlationIds.get(correlationId)).build();
    }

    @Override
    public Response DeleteReservation(int idMunicipio, int idUfficio, int idPrenotazione) {
        if (!prenotazioni.containsKey(idPrenotazione)) {
            return Response.status(404).build();
        }
        prenotazioni.remove(idPrenotazione);
        return Response.status(200).build();
    }

    @Override
    public Response PatchReservation(PatchPrenotazione p, int idMunicipio, int idUfficio, int idPrenotazione) {
        if (!prenotazioni.containsKey(idPrenotazione)) {
            return Response.status(404).build();
        }
        prenotazioni.get(idPrenotazione).dettagli = p;
        return Response.status(200).build();
    }

    @Override
    public Response GetReservation(int idMunicipio, int idUfficio, int idPrenotazione) {
        if (!prenotazioni.containsKey(idPrenotazione)) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(prenotazioni.get(idPrenotazione)).build();
    }
    
}
