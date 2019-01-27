
package modi2018.soapcallback.client.serverreference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per OneWayTest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="OneWayTest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="M" type="{http://server.soapcallback.modi2018/}mType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OneWayTest", propOrder = {
    "m"
})
public class OneWayTest {

    @XmlElement(name = "M")
    protected MType m;

    /**
     * Recupera il valore della proprietà m.
     * 
     * @return
     *     possible object is
     *     {@link MType }
     *     
     */
    public MType getM() {
        return m;
    }

    /**
     * Imposta il valore della proprietà m.
     * 
     * @param value
     *     allowed object is
     *     {@link MType }
     *     
     */
    public void setM(MType value) {
        this.m = value;
    }

}
