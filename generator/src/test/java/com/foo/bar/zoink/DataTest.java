package com.foo.bar.zoink;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.StringReader;
import java.io.StringWriter;

class DataTest {

    private final ObjectFactory objectFactory = new ObjectFactory();

    private JAXBContext jaxbContext;

    @BeforeEach
    void setUp() throws JAXBException {
        jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
    }

    @Test
    void remarshalTest() throws DatatypeConfigurationException, JAXBException {
        Data data1 = new Data();
        XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        cal.setDay(20);
        cal.setMonth(11);
        cal.setYear(2010);
        data1.setCreated(cal);
        System.out.println("created 1: " + data1.getCreated());
        String xml = marshal(data1);
        Data data2 = unmarshal(xml);
        System.out.println("created 2: " + data2.getCreated());
    }

    private String marshal(Data data) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(objectFactory.createData(data), writer);
        return writer.toString();
    }

    @SuppressWarnings("unchecked")
    private Data unmarshal(String xml) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<Data> el = (JAXBElement<Data>) unmarshaller.unmarshal(new StringReader(xml));
        return el.getValue();
    }
}