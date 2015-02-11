package org.opentdc.presents.exception;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.opentdc.presents.PresentData;

@XmlRootElement(name = "Exception")
public class ExceptionData {

	private String message;
	private Map<String, PresentData> data;

	public ExceptionData() {
	}

	public ExceptionData(String message, Map<String, PresentData> data) {
		this.setMessage(message);
		this.setData(data);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static void main(String[] args) throws JAXBException {
		Map<String, PresentData> map = new HashMap<String, PresentData>();
		map.put("0", new PresentData());

		ExceptionData em = new ExceptionData("hello", map);

		JAXBContext context = JAXBContext.newInstance(ExceptionData.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(em, System.out);
	}

	public Map<String, PresentData> getData() {
		return data;
	}

	public void setData(Map<String, PresentData> data) {
		this.data = data;
	}

}
