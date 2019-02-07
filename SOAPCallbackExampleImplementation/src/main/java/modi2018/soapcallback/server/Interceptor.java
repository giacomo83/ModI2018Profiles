/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modi2018.soapcallback.server;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

/**
 *
 * @author Francesco
 */
public class Interceptor extends AbstractSoapInterceptor {
    public Interceptor() {
        super(Phase.SEND);
    }
    
    @Override
    public void handleMessage(SoapMessage t) throws Fault {
        /*for (String h : t.getContextualPropertyKeys()) {
            System.out.println(h);
        }*/
        System.out.println(t.getContextualProperty("HTTP.RESPONSE"));
    }
    
}
