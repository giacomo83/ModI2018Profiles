/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modi2018.soapmom;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.Addressing;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.AttributedURIType;

/**
 *
 * @author Francesco
 */
@WebService(targetNamespace = "http://amministrazioneesempio.it/nomeinterfacciaservizio")
@Addressing(enabled = true, required = false)
public class SOAPMOMInternal {
    @Resource
    private WebServiceContext webServiceContext;
    
    @WebMethod(operationName="MRetrieve")
    public MType PullMessage() {
        try {
            Message message = SOAPMOMServer.consumer.receiveNoWait();
            if (message != null && message instanceof ObjectMessage) {
                ObjectMessage oMessage = (ObjectMessage) message;
                MType returnM = (MType) oMessage.getObject();
                MessageContext messageContext = webServiceContext.getMessageContext();
                AddressingProperties addressProp = new AddressingProperties();
                AttributedURIType messageId = new AttributedURIType();
                messageId.setValue(oMessage.getJMSCorrelationID());
                addressProp.setMessageID(messageId);
                messageContext.put(org.apache.cxf.ws.addressing.JAXWSAConstants.ADDRESSING_PROPERTIES_OUTBOUND, addressProp);
                return returnM;
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    @WebMethod(operationName="MResponsePush")
    public String PushResponseMessage(@WebParam(name = "MResponse") MResponseType M) {
        MessageContext messageContext = webServiceContext.getMessageContext();

        AddressingProperties addressProp = (AddressingProperties) messageContext
                .get(org.apache.cxf.ws.addressing.JAXWSAConstants.ADDRESSING_PROPERTIES_INBOUND);
        try {
            Session session = SOAPMOMServer.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(SOAPMOMServer.destinationQueues.get(SOAPMOMServer.correlationIdToClient.get(addressProp.getRelatesTo().getValue())));
            MessageProducer producer = session.createProducer(destination);
            ObjectMessage message = session.createObjectMessage(M);
            message.setJMSCorrelationID(addressProp.getRelatesTo().getValue());
            producer.send(message);
            return "ACK";
        } catch (JMSException e) {
            e.printStackTrace();
            return "NACK";
        }
    }
}
