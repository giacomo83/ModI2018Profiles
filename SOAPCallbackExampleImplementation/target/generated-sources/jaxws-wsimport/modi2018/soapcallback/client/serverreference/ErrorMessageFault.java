
package modi2018.soapcallback.client.serverreference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per errorMessageFault complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="errorMessageFault">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customFaultCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "errorMessageFault", propOrder = {
    "customFaultCode"
})
public class ErrorMessageFault {

    protected String customFaultCode;

    /**
     * Recupera il valore della proprietà customFaultCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomFaultCode() {
        return customFaultCode;
    }

    /**
     * Imposta il valore della proprietà customFaultCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomFaultCode(String value) {
        this.customFaultCode = value;
    }

}
