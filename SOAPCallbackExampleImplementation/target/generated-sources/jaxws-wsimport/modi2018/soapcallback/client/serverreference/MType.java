
package modi2018.soapcallback.client.serverreference;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per mType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="mType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="o_id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="a" type="{http://amministrazioneesempio.it/nomeinterfacciaservizio}aComplexType" minOccurs="0"/>
 *         &lt;element name="b" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mType", propOrder = {
    "oId",
    "a",
    "b"
})
public class MType {

    @XmlElement(name = "o_id")
    protected Integer oId;
    protected AComplexType a;
    protected String b;

    /**
     * Recupera il valore della proprietà oId.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOId() {
        return oId;
    }

    /**
     * Imposta il valore della proprietà oId.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOId(Integer value) {
        this.oId = value;
    }

    /**
     * Recupera il valore della proprietà a.
     * 
     * @return
     *     possible object is
     *     {@link AComplexType }
     *     
     */
    public AComplexType getA() {
        return a;
    }

    /**
     * Imposta il valore della proprietà a.
     * 
     * @param value
     *     allowed object is
     *     {@link AComplexType }
     *     
     */
    public void setA(AComplexType value) {
        this.a = value;
    }

    /**
     * Recupera il valore della proprietà b.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getB() {
        return b;
    }

    /**
     * Imposta il valore della proprietà b.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setB(String value) {
        this.b = value;
    }

}
