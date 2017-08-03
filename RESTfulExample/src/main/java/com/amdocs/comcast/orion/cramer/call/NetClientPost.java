package com.amdocs.comcast.orion.cramer.call;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import com.amdocs.comcast.orion.cramer.request.request.CreateUNIID;
import com.amdocs.comcast.orion.cramer.request.vlan.CreateVLANID;
import com.amdocs.comcast.orion.cramer.response.AutomatedIDResponse;

public class NetClientPost {

	public static void main(String[] args) {
		createUNIID();
		createVLANID();
	}

	
	public static String objectToXml(Class cls, Object object, String namespaceURI, String localPart) throws JAXBException{
		JAXBContext jaxbContext = JAXBContext.newInstance(cls);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		marshaller.marshal(new JAXBElement<Object>(new QName(namespaceURI, localPart), cls, object), sw);
		return sw.toString();
	}
	
	public static AutomatedIDResponse xmlToObject(String xmlString) throws XMLStreamException, UnsupportedEncodingException, JAXBException{
		InputStream stream = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
		JAXBContext jaxbContext2 = JAXBContext.newInstance(AutomatedIDResponse.class);
		Unmarshaller jaxbUnmarshaller2 = jaxbContext2.createUnmarshaller();
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLEventReader someSource = factory.createXMLEventReader(stream);
		JAXBElement<AutomatedIDResponse> userElement = jaxbUnmarshaller2.unmarshal(someSource, AutomatedIDResponse.class);
		AutomatedIDResponse responseObject = userElement.getValue();
		return responseObject;
	}
	
	public static void createUNIID() {
		try {

			URL url = new URL("http://server:port/RESTAPI/service/automatedIDgeneration/CreateUNIID");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");
			conn.setRequestProperty("dimuser", "bp-jtambe001");

			// preparing input
			CreateUNIID requestObject = new CreateUNIID();
			requestObject.setAccountName("ewrwfswfs");
			requestObject.setBandwidthofUNI("1G");
			requestObject.setCompanyCode("CBCL");
			requestObject.setENNIID("");
			requestObject.setInterorIntraState("Inter");
			requestObject.setLAGGroup("No");
			requestObject.setMarketID("14-BAWA");
			requestObject.setOffNetIndicator("No");
			requestObject.setRequestID("43201");
			requestObject.setSubscriberAccountNumber("12548787");

			String xmlString = objectToXml(CreateUNIID.class, requestObject, "urn:automatedIDgeneration", "uniid");
			System.out.println("input xml : " + xmlString);

			OutputStream os = conn.getOutputStream();
			os.write(xmlString.getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");

			StringBuilder sb = new StringBuilder();
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			System.out.println(sb);
			conn.disconnect();
			
			AutomatedIDResponse object = xmlToObject(xmlString);
			
			System.out.println("");
			System.out.println("object : " + object);

		} catch (XMLStreamException | JAXBException | IOException e) {
			System.out.println("Exception" + e);
		}
	}
	
	
	public static void createVLANID() {
		try {

			URL url = new URL("http://server:port/RESTAPI/service/automatedIDgeneration/CreateVLANID");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");
			conn.setRequestProperty("dimuser", "bp-jtambe001");

			// preparing input
			CreateVLANID requestObject = new CreateVLANID();
			requestObject.setAccountName("ewrwfswfs");
			requestObject.setELANorELINEIndicator("ELINE");
			requestObject.setNNIID("14.KFGD.900023..CBCL.");
			requestObject.setNumberofVLANIDsRequested(2);
			requestObject.setRequestID("432012");
			requestObject.setSubscriberAccountNumber("125487817");

			String xmlString = objectToXml(CreateVLANID.class, requestObject, "urn:automatedIDgeneration", "vlanids");
			System.out.println("input xml : " + xmlString);

			OutputStream os = conn.getOutputStream();
			os.write(xmlString.getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");

			StringBuilder sb = new StringBuilder();
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			System.out.println(sb);
			conn.disconnect();

			AutomatedIDResponse object = xmlToObject(xmlString);

			System.out.println("");
			System.out.println("object : " + object);

		} catch (XMLStreamException | JAXBException | IOException e) {
			System.out.println("MalformedURLException" + e);
		}
	}
	
	
}