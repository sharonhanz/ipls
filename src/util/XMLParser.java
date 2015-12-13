package util;

import java.io.StringReader;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

public class XMLParser {
	private String type = null;
	private String data = null;

	public XMLParser(String type) {
		this.type = type.toUpperCase();
		this.data = "";
	}

	public boolean add(String method, String target, String value) {
		boolean ret = true;
		String addData = "";
		if (method.equals("set")) {
			addData = "<Operation-set><Target>" + target + "</Target><Value>"
					+ value + "</Value></Operation-set>";
		} else if (method.equals("add")) {
			addData = "<Operation-add><Target>" + target + "</Target><Value>"
					+ value + "</Value></Operation-add>";
		} else if (method.equals("remove")) {
			addData = "<Operation-remove><Target>" + target
					+ "</Target><Value>" + value
					+ "</Value></Operation-remove>";
		} else {
			ret = false;
		}
		data += addData;
		return ret;
	}

	public String getXML() {
		String ret = null;
		if (this.type.equals("PUT")) {
			ret = "<PUT>" + this.data + "</PUT>";
		} else if (this.type.equals("POST")) {
			ret = "<POST>" + this.data + "</POST>";
		}
		return ret;
	}

	public String getPostCreated(String xml) {
		String url = "";
		try {
			StringReader read = new StringReader(xml);
			InputSource inputSource = new InputSource(read);
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(inputSource);
			Element post = doc.getRootElement();
			Element created = post.getChild("Created");
			url = created.getText();
		} catch (Exception e) {
			// do nothing
		}
		return url;
	}
}
