/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modi2018.restcrud;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Francesco
 */
public class PatchPrenotazione implements Serializable {
    public Date data;
    public String ora;
    public String motivazione;
}
