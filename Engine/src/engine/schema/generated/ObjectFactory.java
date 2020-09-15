//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.21 at 02:28:52 PM IDT 
//


package engine.schema.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the engine.schema.generated package. 
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

    private final static QName _Owner_QNAME = new QName("", "Owner");
    private final static QName _Length_QNAME = new QName("", "Length");
    private final static QName _SpeedLimit_QNAME = new QName("", "SpeedLimit");
    private final static QName _Capacity_QNAME = new QName("", "Capacity");
    private final static QName _PPK_QNAME = new QName("", "PPK");
    private final static QName _FuelConsumption_QNAME = new QName("", "FuelConsumption");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: engine.schema.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Path }
     * 
     */
    public Path createPath() {
        return new Path();
    }

    /**
     * Create an instance of {@link PlannedTrips }
     * 
     */
    public PlannedTrips createPlannedTrips() {
        return new PlannedTrips();
    }

    /**
     * Create an instance of {@link TransPoolTrip }
     * 
     */
    public TransPoolTrip createTransPoolTrip() {
        return new TransPoolTrip();
    }

    /**
     * Create an instance of {@link Route }
     * 
     */
    public Route createRoute() {
        return new Route();
    }

    /**
     * Create an instance of {@link Scheduling }
     * 
     */
    public Scheduling createScheduling() {
        return new Scheduling();
    }

    /**
     * Create an instance of {@link TransPool }
     * 
     */
    public TransPool createTransPool() {
        return new TransPool();
    }

    /**
     * Create an instance of {@link MapDescriptor }
     * 
     */
    public MapDescriptor createMapDescriptor() {
        return new MapDescriptor();
    }

    /**
     * Create an instance of {@link MapBoundries }
     * 
     */
    public MapBoundries createMapBoundries() {
        return new MapBoundries();
    }

    /**
     * Create an instance of {@link Stops }
     * 
     */
    public Stops createStops() {
        return new Stops();
    }

    /**
     * Create an instance of {@link Stop }
     * 
     */
    public Stop createStop() {
        return new Stop();
    }

    /**
     * Create an instance of {@link Paths }
     * 
     */
    public Paths createPaths() {
        return new Paths();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Owner")
    public JAXBElement<String> createOwner(String value) {
        return new JAXBElement<String>(_Owner_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Length")
    public JAXBElement<Integer> createLength(Integer value) {
        return new JAXBElement<Integer>(_Length_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SpeedLimit")
    public JAXBElement<Integer> createSpeedLimit(Integer value) {
        return new JAXBElement<Integer>(_SpeedLimit_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Capacity")
    public JAXBElement<Integer> createCapacity(Integer value) {
        return new JAXBElement<Integer>(_Capacity_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PPK")
    public JAXBElement<Integer> createPPK(Integer value) {
        return new JAXBElement<Integer>(_PPK_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "FuelConsumption")
    public JAXBElement<Integer> createFuelConsumption(Integer value) {
        return new JAXBElement<Integer>(_FuelConsumption_QNAME, Integer.class, null, value);
    }

}
