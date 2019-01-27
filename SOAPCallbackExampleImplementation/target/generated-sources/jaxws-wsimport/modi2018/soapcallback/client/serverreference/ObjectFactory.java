
package modi2018.soapcallback.client.serverreference;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the modi2018.soapcallback.client.serverreference package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ErrorMessageFault_QNAME = new QName("http://amministrazioneesempio.it/nomeinterfacciaservizio", "ErrorMessageFault");
    private final static QName _MResponse_QNAME = new QName("http://amministrazioneesempio.it/nomeinterfacciaservizio", "MResponse");
    private final static QName _M_QNAME = new QName("http://amministrazioneesempio.it/nomeinterfacciaservizio", "M");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: modi2018.soapcallback.client.serverreference
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ErrorMessageFault }
     * 
     */
    public ErrorMessageFault createErrorMessageFault() {
        return new ErrorMessageFault();
    }

    /**
     * Create an instance of {@link MResponse }
     * 
     */
    public MResponse createMResponse() {
        return new MResponse();
    }

    /**
     * Create an instance of {@link M }
     * 
     */
    public M createM() {
        return new M();
    }

    /**
     * Create an instance of {@link MResponseType }
     * 
     */
    public MResponseType createMResponseType() {
        return new MResponseType();
    }

    /**
     * Create an instance of {@link MType }
     * 
     */
    public MType createMType() {
        return new MType();
    }

    /**
     * Create an instance of {@link AComplexType }
     * 
     */
    public AComplexType createAComplexType() {
        return new AComplexType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorMessageFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://amministrazioneesempio.it/nomeinterfacciaservizio", name = "ErrorMessageFault")
    public JAXBElement<ErrorMessageFault> createErrorMessageFault(ErrorMessageFault value) {
        return new JAXBElement<ErrorMessageFault>(_ErrorMessageFault_QNAME, ErrorMessageFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://amministrazioneesempio.it/nomeinterfacciaservizio", name = "MResponse")
    public JAXBElement<MResponse> createMResponse(MResponse value) {
        return new JAXBElement<MResponse>(_MResponse_QNAME, MResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link M }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://amministrazioneesempio.it/nomeinterfacciaservizio", name = "M")
    public JAXBElement<M> createM(M value) {
        return new JAXBElement<M>(_M_QNAME, M.class, null, value);
    }

}
