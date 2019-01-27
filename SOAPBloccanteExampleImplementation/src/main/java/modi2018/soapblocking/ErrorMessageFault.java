/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modi2018.soapblocking;

/**
 *
 * @author Francesco
 */
public class ErrorMessageFault {
	/**
	 * Fault Info
	 */
	 private String customFaultCode;
	/**
	 * @return the faultCode
	 */
	public String getCustomFaultCode() {
		return customFaultCode;
	}
	/**
	 * @param faultCode the faultCode to set
	 */
	public void setCustomFaultCode(String customFaultCode) {
		this.customFaultCode = customFaultCode;
	}
}
