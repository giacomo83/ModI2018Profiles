package modi2018.soapmom;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.Addressing;

import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.RelatesToType;

@WebService(targetNamespace = "http://amministrazioneesempio.it/nomeinterfacciaservizio")
@Addressing(enabled = true, required = false)
public class SOAPMOMImpl {

    @Resource
    private WebServiceContext webServiceContext;

    @WebMethod(operationName="MRequest")
    public PushMessageReturn PushMessage(@WebParam(name = "M") MType M, @WebParam(name = "clientId") String clientId) {
        MessageContext messageContext = webServiceContext.getMessageContext();

        AddressingProperties addressProp = (AddressingProperties) messageContext
                .get(org.apache.cxf.ws.addressing.JAXWSAConstants.ADDRESSING_PROPERTIES_INBOUND);

        try {
            Session session = SOAPMOMServer.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("MQUEUE");
            MessageProducer producer = session.createProducer(destination);
            ObjectMessage message = session.createObjectMessage(M);
            message.setJMSCorrelationID(addressProp.getMessageID().getValue());
            Queue replyToQueue = null;
            if (SOAPMOMServer.destinationQueues.containsKey(clientId)) {
                replyToQueue = session.createQueue(SOAPMOMServer.destinationQueues.get(clientId));
            } else  {
                replyToQueue = session.createTemporaryQueue();
                SOAPMOMServer.destinationQueues.put(clientId, replyToQueue.getQueueName());
            }
            message.setJMSReplyTo(replyToQueue);
            producer.send(message);
            SOAPMOMServer.destinationQueues.put(clientId, replyToQueue.getQueueName());
            SOAPMOMServer.correlationIdToClient.put(addressProp.getMessageID().getValue(), clientId);
            return new PushMessageReturn("ACK");
        } catch (JMSException e) {
            e.printStackTrace();
            return new PushMessageReturn("NACK");
        }
    }

    @WebMethod(operationName="MResponse")
    public MResponseType PullResponseMessage(@WebParam(name = "clientId") String clientId) {
        MessageContext messageContext = webServiceContext.getMessageContext();

        try {
            Session session = SOAPMOMServer.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(SOAPMOMServer.destinationQueues.get(clientId));
            MessageConsumer consumer = session.createConsumer(destination);
            Message message = consumer.receiveNoWait();
            if (message != null && message instanceof ObjectMessage) {
                ObjectMessage oMessage = (ObjectMessage) message;
                MResponseType returnM = (MResponseType) oMessage.getObject();
                AddressingProperties addressProp = new AddressingProperties();
                RelatesToType messageId = new RelatesToType();
                messageId.setValue(oMessage.getJMSCorrelationID());
                addressProp.setRelatesTo(messageId);
                messageContext.put(org.apache.cxf.ws.addressing.JAXWSAConstants.ADDRESSING_PROPERTIES_OUTBOUND, addressProp);
                return returnM;
            }
        } catch (JMSException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
