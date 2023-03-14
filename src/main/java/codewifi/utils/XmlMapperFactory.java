package codewifi.utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlMapperFactory {

	private static final XmlMapper XML_MAPPER = new XmlMapper();

	public static XmlMapper getXmlMapper() {
		return XML_MAPPER;
	}

}
