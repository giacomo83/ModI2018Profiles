/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modi2018.restmom;

import java.io.Serializable;

/**
 *
 * @author Francesco
 */
public class ErrorMessage implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ErrorMessage(String error_message) {
        this.error_message = error_message;
    }
    public String error_message;
}
