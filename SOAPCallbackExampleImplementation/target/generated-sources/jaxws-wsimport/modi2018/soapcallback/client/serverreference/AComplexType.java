
package modi2018.soapcallback.client.serverreference;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per aComplexType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="aComplexType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="a1s" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="a2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aComplexType", propOrder = {
    "a1S",
    "a2"
})
public class AComplexType {

    @XmlElement(name = "a1s", nillable = true)
    protected List<String> a1S;
    protected String a2;

    /**
     * Gets the value of the a1S property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the a1S property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getA1S().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getA1S() {
        if (a1S == null) {
            a1S = new ArrayList<String>();
        }
        return this.a1S;
    }

    /**
     * Recupera il valore della proprietà a2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getA2() {
        return a2;
    }

    /**
     * Imposta il valore della proprietà a2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setA2(String value) {
        this.a2 = value;
    }

}
