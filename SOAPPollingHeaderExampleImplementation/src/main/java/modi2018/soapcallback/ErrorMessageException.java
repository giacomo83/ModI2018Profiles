/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modi2018.soapcallback;

import javax.xml.ws.WebFault;

/**
 *
 * @author Francesco
 */
@WebFault(name="ErrorMessageFault")
public class ErrorMessageException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6647544772732631047L;
	private ErrorMessageFault fault;
	/**
	 * 
	 */
	public ErrorMessageException() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param fault
	 */
	protected ErrorMessageException(ErrorMessageFault fault) {
        super("Generic Error"); 
        this.fault = fault;
     }
	/**
	 * 
	 * @param message
	 * @param faultInfo
	 */
	public ErrorMessageException(String message, ErrorMessageFault faultInfo){
		super(message);
        this.fault = faultInfo;
	}
	/**
	 * 
	 * @param message
	 * @param faultInfo
	 * @param cause
	 */
	public ErrorMessageException(String message, ErrorMessageFault faultInfo, Throwable cause){
		super(message,cause);
        this.fault = faultInfo;
	}
	/**
	 * 
	 * @return
	 */
	public ErrorMessageFault getFaultInfo(){
		return fault;
	}
	
	/**
	 * @param message
	 */
	public ErrorMessageException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param message
	 */
	public ErrorMessageException(String code, String message) {
		super(message);
		this.fault = new ErrorMessageFault();
	    this.fault.setCustomFaultCode(code);
	}

	/**
	 * @param cause
	 */
	public ErrorMessageException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ErrorMessageException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
}
