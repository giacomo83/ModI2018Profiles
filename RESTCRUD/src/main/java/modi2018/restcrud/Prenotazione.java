/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modi2018.restcrud;

import java.io.Serializable;

/**
 *
 * @author Francesco
 */
public class Prenotazione implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String nome;
    public String cognome;
    public String cf;
    public PatchPrenotazione dettagli;
}
